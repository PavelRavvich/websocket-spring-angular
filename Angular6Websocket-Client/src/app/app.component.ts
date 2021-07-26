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

  producerId = '68b0439b-82e7-4aa0-adec-cdd4e877f7ba';
  consumerId = 11;

  private token: string = 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTYyNzE1MDYyNywiZXhwIjoxNjI3NzU1NDI3fQ.b-ijFWqZK3xNxPZuRdN2gK-anG3WPJn0vEHd06qV4zs';

  constructor() { }

  setConnected(connected: boolean) {
    this.disabled = !connected;

    if (connected) {
      this.orders = [];
    }
  }

  connect() {
    const socket = new SockJS('http://localhost:8081/api/streams');
    this.stompClient = Stomp.over(socket);

    const _this = this;
    this.stompClient.connect({Authorization: _this.token}, function (frame) {
      _this.setConnected(true);
      console.log('Connected: ' + frame);

      _this.stompClient.subscribe(`/stream/newOrder/${_this.producerId}`, function (payload) {
        _this.showGreeting(JSON.parse(payload.body).data);
        console.log(payload);
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
      {Authorization: this.token},
      JSON.stringify({ 'data': 'some data' })
    );
  }

  showGreeting(order) {
    this.orders.push(order);
  }
}
