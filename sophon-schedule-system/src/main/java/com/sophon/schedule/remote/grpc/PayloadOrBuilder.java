// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: grpc-service.proto

package com.sophon.schedule.remote.grpc;

public interface PayloadOrBuilder extends
    // @@protoc_insertion_point(interface_extends:Payload)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>.Metadata metadata = 2;</code>
   * @return Whether the metadata field is set.
   */
  boolean hasMetadata();
  /**
   * <code>.Metadata metadata = 2;</code>
   * @return The metadata.
   */
  Metadata getMetadata();
  /**
   * <code>.Metadata metadata = 2;</code>
   */
  MetadataOrBuilder getMetadataOrBuilder();

  /**
   * <code>.google.protobuf.Any body = 3;</code>
   * @return Whether the body field is set.
   */
  boolean hasBody();
  /**
   * <code>.google.protobuf.Any body = 3;</code>
   * @return The body.
   */
  com.google.protobuf.Any getBody();
  /**
   * <code>.google.protobuf.Any body = 3;</code>
   */
  com.google.protobuf.AnyOrBuilder getBodyOrBuilder();
}
