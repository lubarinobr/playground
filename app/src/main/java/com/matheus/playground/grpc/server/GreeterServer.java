package com.matheus.playground.grpc.server;

import com.matheus.playground.grpc.GreeterGrpc;
import com.matheus.playground.grpc.HelloRequest;
import com.matheus.playground.grpc.HelloResponse;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class GreeterServer extends GreeterGrpc.GreeterImplBase {

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
        final String name = request.getName();
        final String message = "Hello " + name + "!";
        HelloResponse build = HelloResponse.newBuilder().setMessage(message).build();
        responseObserver.onNext(build);
        responseObserver.onCompleted();
    }
}
