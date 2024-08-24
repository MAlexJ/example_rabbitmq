### What is the Request/Response Pattern?

link: https://reflectoring.io/amqp-request-response/

The request/response interaction between two parties is pretty easy. The client sends a request to the server, the
server starts the work and sends the response to the client once the work is done.

The best-known example of this interaction is communication via the HTTP protocol, where the request and response are
sent through the same channel / the same connection.

Normally, the client sends the request directly to the server and waits for the response synchronously. In this case,
the client has to know the API of the server.

### Why Do We Need an Async Request/Response Pattern?

A software enterprise system consists of many components. T
hese components communicate with each other.
Sometimes it is enough just to send a message to another component and not wait for an answer.
But in many cases, a component may need to get the response to a request.

When we use direct synchronous communication, the client has to know the API of the server.
When one component has a big number of different API calls to another component,
we’re building coupling them to eath other tightly, and the whole picture can become hard to change.

To reduce the coupling a bit we can use a message broker as a central component for communication between the
components,
instead of a synchronous protocol.

#### Asynchronous Communication

Since we use messaging for requests and responses, the communication is now working asynchronously.

Here’s how it works:

* The client sends the request to the request channel.
* The server consumes the request from the request channel.
* The server sends the response to the response channel.
* The client consumes the response from the response channel.
* When the client sends a request, it waits for the response by listening to the response channel.
  If the client sends many requests, then it expects a response for every request.
  But how does the client know which response is for which request?

To solve this problem, the client should send a unique correlation IDentifier along with each request.
The server should obtain this identifier and add it to the response.
Now the client can assign a response to its request.

### The important things are:

* We have two channels. One for requests and one for responses.
* We use a correlation ID on both ends of the communication.