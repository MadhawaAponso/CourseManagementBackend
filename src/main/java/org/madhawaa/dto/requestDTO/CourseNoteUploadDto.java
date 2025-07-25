//// src/main/java/org/madhawaa/dto/requestDTO/CourseNoteUploadForm.java
//package org.madhawaa.dto.requestDTO;
//
//import org.jboss.resteasy.reactive.MultipartForm;
//
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;
//import org.jboss.resteasy.reactive.RestForm;
//import io.vertx.mutiny.core.buffer.Buffer;
//
//@MultipartForm
//public class CourseNoteUploadForm {
//
//    @RestForm
//    @NotNull
//    public Integer lectureId;
//
//    @RestForm
//    @NotBlank
//    public String noteTitle;
//
//    // this is the binary payload
//    @RestForm("file")
//    public io.vertx.mutiny.core.file.FileUpload file;
//}
