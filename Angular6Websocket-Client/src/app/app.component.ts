import { Component } from '@angular/core';
import * as Stomp from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  orders: string[] = [];
  disabled = true;
  name: string;
  private stompClient = null;

  producerId = 2;
  consumerId = 11;

  constructor() { }

  setConnected(connected: boolean) {
    this.disabled = !connected;

    if (connected) {
      this.orders = [];
    }
  }

  connect() {
    const socket = new SockJS('http://localhost:8080/api/streams');
    this.stompClient = Stomp.over(socket);

    const _this = this;
    this.stompClient.connect({}, function (frame) {
      _this.setConnected(true);
      console.log('Connected: ' + frame);

      _this.stompClient.subscribe(`/stream/newOrder/${_this.producerId}`, function (payload) {
        _this.showGreeting(JSON.parse(payload.body).data);
      });
    });
  }

  disconnect() {
    if (this.stompClient != null) {
      this.stompClient.disconnect();
    }

    this.setConnected(false);
    console.log('Disconnected!');
  }

  sendName() {
    this.stompClient.send(
      `/api/newOrders/${this.producerId}/${this.consumerId}`,
      {},
      JSON.stringify({ 'data': 'some data' })
    );
  }

  showGreeting(order) {
    this.orders.push(order);
  }
}
