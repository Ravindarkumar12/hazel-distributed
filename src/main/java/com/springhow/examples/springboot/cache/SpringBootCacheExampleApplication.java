package com.springhow.examples.springboot.cache;

import com.springhow.examples.springboot.cache.entities.Item;
import com.springhow.examples.springboot.cache.service.ItemService;
import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@EnableCaching
@SpringBootApplication
public class SpringBootCacheExampleApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(SpringBootCacheExampleApplication.class);

    @Autowired
    private ItemService itemService;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootCacheExampleApplication.class, args);
    }

//    @Override
//    public void run(String... args) throws Exception {
//        logger.info("Loading test data.");
//        itemService.createItem(new Item("xxyy", 1));
//        itemService.createItem(new Item("xxxyyy", 2));
//        itemService.createItem(new Item("xyz", 1));
//    }

    @RestController
    public static class CustomTraceController {
        private final Tracer tracer = GlobalOpenTelemetry.getTracer("custom-tracer");

        @GetMapping("/custom-trace1")
        public String customTrace() {
            Span span = tracer.spanBuilder("custom-span").startSpan();

            try (Scope scope = span.makeCurrent()) {
                // Set custom TraceId and SpanId
                span.setAttribute("custom-trace-id", "your-custom-trace-id");
                span.setAttribute("custom-span-id", "your-custom-span-id");

                // Simulate some work
                Thread.sleep(100);

                return "Custom Trace and Span Ids Set";
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return "Error occurred during processing";
            } finally {
                span.end();
            }
        }
    }
}
