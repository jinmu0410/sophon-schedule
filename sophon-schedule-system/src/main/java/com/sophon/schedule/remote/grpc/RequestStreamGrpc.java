package com.sophon.schedule.remote.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.59.0)",
    comments = "Source: remote.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class RequestStreamGrpc {

  private RequestStreamGrpc() {}

  public static final String SERVICE_NAME = "RequestStream";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<Payload,
      Payload> getRequestStreamMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "requestStream",
      requestType = Payload.class,
      responseType = Payload.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<Payload,
      Payload> getRequestStreamMethod() {
    io.grpc.MethodDescriptor<Payload, Payload> getRequestStreamMethod;
    if ((getRequestStreamMethod = RequestStreamGrpc.getRequestStreamMethod) == null) {
      synchronized (RequestStreamGrpc.class) {
        if ((getRequestStreamMethod = RequestStreamGrpc.getRequestStreamMethod) == null) {
          RequestStreamGrpc.getRequestStreamMethod = getRequestStreamMethod =
              io.grpc.MethodDescriptor.<Payload, Payload>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "requestStream"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Payload.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Payload.getDefaultInstance()))
              .setSchemaDescriptor(new RequestStreamMethodDescriptorSupplier("requestStream"))
              .build();
        }
      }
    }
    return getRequestStreamMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static RequestStreamStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<RequestStreamStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<RequestStreamStub>() {
        @Override
        public RequestStreamStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new RequestStreamStub(channel, callOptions);
        }
      };
    return RequestStreamStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static RequestStreamBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<RequestStreamBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<RequestStreamBlockingStub>() {
        @Override
        public RequestStreamBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new RequestStreamBlockingStub(channel, callOptions);
        }
      };
    return RequestStreamBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static RequestStreamFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<RequestStreamFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<RequestStreamFutureStub>() {
        @Override
        public RequestStreamFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new RequestStreamFutureStub(channel, callOptions);
        }
      };
    return RequestStreamFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default io.grpc.stub.StreamObserver<Payload> requestStream(
        io.grpc.stub.StreamObserver<Payload> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getRequestStreamMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service RequestStream.
   */
  public static abstract class RequestStreamImplBase
      implements io.grpc.BindableService, AsyncService {

    @Override public final io.grpc.ServerServiceDefinition bindService() {
      return RequestStreamGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service RequestStream.
   */
  public static final class RequestStreamStub
      extends io.grpc.stub.AbstractAsyncStub<RequestStreamStub> {
    private RequestStreamStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected RequestStreamStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new RequestStreamStub(channel, callOptions);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<Payload> requestStream(
        io.grpc.stub.StreamObserver<Payload> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncBidiStreamingCall(
          getChannel().newCall(getRequestStreamMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service RequestStream.
   */
  public static final class RequestStreamBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<RequestStreamBlockingStub> {
    private RequestStreamBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected RequestStreamBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new RequestStreamBlockingStub(channel, callOptions);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service RequestStream.
   */
  public static final class RequestStreamFutureStub
      extends io.grpc.stub.AbstractFutureStub<RequestStreamFutureStub> {
    private RequestStreamFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected RequestStreamFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new RequestStreamFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_REQUEST_STREAM = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }

    @Override
    @SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_REQUEST_STREAM:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.requestStream(
              (io.grpc.stub.StreamObserver<Payload>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getRequestStreamMethod(),
          io.grpc.stub.ServerCalls.asyncBidiStreamingCall(
            new MethodHandlers<
              Payload,
              Payload>(
                service, METHODID_REQUEST_STREAM)))
        .build();
  }

  private static abstract class RequestStreamBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    RequestStreamBaseDescriptorSupplier() {}

    @Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return Remote.getDescriptor();
    }

    @Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("RequestStream");
    }
  }

  private static final class RequestStreamFileDescriptorSupplier
      extends RequestStreamBaseDescriptorSupplier {
    RequestStreamFileDescriptorSupplier() {}
  }

  private static final class RequestStreamMethodDescriptorSupplier
      extends RequestStreamBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    RequestStreamMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (RequestStreamGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new RequestStreamFileDescriptorSupplier())
              .addMethod(getRequestStreamMethod())
              .build();
        }
      }
    }
    return result;
  }
}
