package com.project.demo.rest.reporte;

import com.project.demo.logic.entity.http.GlobalResponseHandler;
import com.project.demo.logic.entity.reporte.ReporteService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequestMapping({"/reportes", "/api/reportes"})
public class ReporteRestController {
    private final ReporteService reporteService;

    public ReporteRestController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @GetMapping("/exportar")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<?> exportReport(
            @RequestParam(name = "formato", defaultValue = "csv") String formato,
            HttpServletRequest request
    ) {
        String normalizedFormat = formato == null ? "" : formato.trim().toLowerCase(Locale.ROOT);
        if ("csv".equals(normalizedFormat)) {
            byte[] content = reporteService.exportCsv();
            HttpHeaders headers = buildHeaders("reporte.csv", MediaType.parseMediaType("text/csv"), content.length);
            return new ResponseEntity<>(content, headers, HttpStatus.OK);
        }

        if ("pdf".equals(normalizedFormat)) {
            byte[] content = reporteService.exportPdf();
            HttpHeaders headers = buildHeaders("reporte.pdf", MediaType.APPLICATION_PDF, content.length);
            return new ResponseEntity<>(content, headers, HttpStatus.OK);
        }

        return new GlobalResponseHandler().handleResponse(
                "Formato no soportado.",
                HttpStatus.BAD_REQUEST,
                request
        );
    }

    private HttpHeaders buildHeaders(String fileName, MediaType mediaType, long contentLength) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);
        headers.setContentDisposition(ContentDisposition.attachment().filename(fileName).build());
        headers.setContentLength(contentLength);
        headers.setCacheControl("no-cache, no-store, must-revalidate");
        headers.setPragma("no-cache");
        headers.setExpires(0);
        return headers;
    }
}
