import { Client } from '@stomp/stompjs';
import { WebSocket } from 'ws';
Object.assign(global, { WebSocket });

const privateClient1 = new Client({
  brokerURL: 'ws://localhost:8080/private-chat',
  onConnect: () => {
    console.log("privateClient1 connected");
    let queue = "/user/queue/reply";
    let callback = function(message) {
      if (message.body) {
        console.log("Private Client 1 got message with body " + message.body)
      } else {
        console.log("got empty message");
      }
    };

    privateClient1.subscribe(queue, callback);
    privateClient1.publish({
      destination: '/app/private-chat',
      body: JSON.stringify({from: "Private Client 1",msg : "Hi, There!"})
    });
  },
});

const privateClient2 = new Client({
  brokerURL: 'ws://localhost:8080/private-chat',
  onConnect: () => {
    console.log("privateClient2 connected");
    let queue = "/user/queue/reply";
    let callback = function(message) {
      if (message.body) {
        console.log("Private Client 2 got message with body " + message.body)
      } else {
        console.log("got empty message");
      }
    };

    privateClient2.subscribe(queue, callback);
    privateClient2.publish({
      destination: '/app/private-chat',
      body: JSON.stringify({from: "Private Client 2",msg : "Hi, There!"})
    });
  },
});

privateClient1.activate();
privateClient2.activate();

const client1 = new Client({
  brokerURL: 'ws://localhost:8080/chat',
  onConnect: () => {
    console.log("client1 connected");
    let queue = "/topic/messages";
    let callback = function(message) {
      if (message.body) {
        console.log("Public Client 1 got message with body " + message.body)
      } else {
        console.log("got empty message");
      }
    };

    client1.subscribe(queue, callback);
    client1.publish({
      destination: '/app/chat',
      body: JSON.stringify({from: "Public Client 1",msg : "Hi, There!"})
    });
  },
});

const client2 = new Client({
  brokerURL: 'ws://localhost:8080/chat',
  onConnect: () => {
    console.log("client2 connected");
    let queue = "/topic/messages";
    let callback = function(message) {
      if (message.body) {
        console.log("Public Client 2 got message with body " + message.body)
      } else {
        console.log("got empty message");
      }
    };

    client2.subscribe(queue, callback);
  },
});

client1.activate();
client2.activate();
