
package com.nyashia.resarch_mate.service;

import java.time.Duration;
import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import com.nyashia.resarch_mate.client.SemanticScholarClient;
import com.nyashia.resarch_mate.dto.PaperDto;

import reactor.core.publisher.Flux;

@Service
public class ReviewService {

    private final SemanticScholarClient semanticScholarClient;
    private final ChatClient chatClient;

    public ReviewService(
            SemanticScholarClient semanticScholarClient,
            ChatClient.Builder chatClientBuilder
    ) {
        this.semanticScholarClient = semanticScholarClient;
        this.chatClient = chatClientBuilder.build();
    }

    public Flux<String> generateReview(List<String> paperIds) {

        //Fetch the papers
        List<PaperDto> papers =
                semanticScholarClient.getPapersByIds(paperIds);

        //Handle the case where no usable papers were found
        if (papers.isEmpty()) {
            return Flux.just(
                    "I couldn't find any papers with usable abstracts. " +
                    "Please try different paper IDs."
            );
        }

        //Combine the paper titles and abstracts
        StringBuilder paperContext = new StringBuilder();

        for (int i = 0; i < papers.size(); i++) {
            PaperDto paper = papers.get(i);

            paperContext.append("Paper ")
                    .append(i + 1)
                    .append(": ")
                    .append(paper.title())
                    .append("\nAbstract: ")
                    .append(paper.abstractText())
                    .append("\n\n");
        }

        //Ask the AI to compare the papers and stream its response
        return chatClient.prompt()
                .user("""
                        You are a research assistant helping a student
                        write a literature review.

                        Analyze the following research papers together.

                        Your review must:
                        1. Identify common themes and findings.
                        2. Compare their research methods and approaches.
                        3. Highlight disagreements or different perspectives.
                        4. Identify research gaps and unanswered questions.
                        5. Refer to studies by their paper numbers and titles.

                        Write in clear, plain English.
                        Keep the review under 400 words.
                        Base your analysis on the supplied abstracts.
                        Do not invent findings that are not supported by them.

                        Research papers:

                        %s
                        """.formatted(paperContext))
                .stream()
                .content()
                .timeout(Duration.ofSeconds(120));
    }
}
