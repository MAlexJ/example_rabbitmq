package com.malexj.rest;

import com.malexj.producer.Document;
import com.malexj.service.DocumentService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/documents")
@RequiredArgsConstructor
public class DocumentController {

  private final DocumentService service;

  @GetMapping
  public ResponseEntity<List<DocumentResponse>> findAll() {
    var documents = service.findAll().stream().map(DocumentResponse::new).toList();
    return ResponseEntity.ok(documents);
  }

  @PostMapping
  public ResponseEntity<DocumentResponse> publish(@RequestBody DocumentRequest request) {
    var document = service.publish(request.name(), request.type());
    return ResponseEntity.ok(new DocumentResponse(document));
  }

  public record DocumentRequest(String name, String type) {
    public DocumentRequest {
      Objects.requireNonNull(name, "the 'name' field cannot be null");
      Objects.requireNonNull(type, "the 'type' field cannot be null");
    }
  }

  public record DocumentResponse(String uuid, String name, String type, LocalDateTime timestamp) {
    public DocumentResponse(Document document) {
      this(document.uuid(), document.name(), document.type().toString(), document.timestamp());
    }
  }
}
