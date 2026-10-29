package com.nyashia.resarch_mate.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("mock-ai")
public class MockSummarizationService implements SummarizationService {

    @Override
    public String summarize(String paperId, String abstractText) {
        return "This is a mock summary.";
    }
}