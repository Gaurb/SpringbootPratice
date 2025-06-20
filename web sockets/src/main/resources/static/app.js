let stompClient = null;
let username = null;

function connect() {
    username = $("#username").val().trim();
    if (username === "") {
        alert("Please enter a username");
        return;
    }

    const socket = new SockJS('http://localhost:8080/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    
    // Enable debug mode
    stompClient.debug = function(str) {
        console.log(str);
    };
    
    stompClient.connect({}, function(frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        
        // Subscribe to private messages
        const subscription = stompClient.subscribe('/user/' + username + '/queue/private', function(message) {
            console.log('Received message:', message);
            const privateMessage = JSON.parse(message.body);
            console.log('Parsed message:', privateMessage);
            showMessage(privateMessage);
        });
        
        console.log('Subscribed to: /user/' + username + '/queue/private');
    }, function(error) {
        console.error('Error: ' + error);
        setConnected(false);
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendMessage() {
    const recipient = $("#recipient").val().trim();
    const content = $("#message").val().trim();
    
    if (recipient === "" || content === "") {
        alert("Please enter both recipient and message");
        return;
    }

    const privateMessage = {
        from: username,
        to: recipient,
        content: content
    };

    console.log('Sending message:', privateMessage);
    stompClient.send("/app/private-message", {}, JSON.stringify(privateMessage));
    $("#message").val('');
}

function showMessage(message) {
    console.log('Showing message:', message);
    const isSent = message.from === username;
    const messageClass = isSent ? 'sent' : 'received';
    const messageHtml = `
        <div class="message ${messageClass}">
            <strong>${message.from}</strong>: ${message.content}
        </div>
    `;
    $("#chat-messages").append(messageHtml);
    // Scroll to bottom
    const chatContainer = document.getElementById('chat-messages');
    chatContainer.scrollTop = chatContainer.scrollHeight;
}

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    $("#send").prop("disabled", !connected);
    $("#username").prop("disabled", connected);
    if (connected) {
        $("#chat-messages").show();
    } else {
        $("#chat-messages").hide();
    }
    $("#chat-messages").html("");
}

$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $("#connect").click(() => connect());
    $("#disconnect").click(() => disconnect());
    $("#send").click(() => sendMessage());
});