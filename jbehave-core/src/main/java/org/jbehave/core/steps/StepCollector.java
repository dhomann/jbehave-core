package org.jbehave.core.steps;

import java.util.List;
import java.util.Map;

import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.AfterStories;
import org.jbehave.core.annotations.AfterStory;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.BeforeStories;
import org.jbehave.core.annotations.BeforeStory;
import org.jbehave.core.model.Meta;
import org.jbehave.core.model.Scenario;
import org.jbehave.core.model.Story;

/**
 * Represents the strategy for the collection of executable {@link Step}s from a
 * story or scenario matching a list of {@link CandidateSteps}. It also collects the 
 * steps to run at before/after stages.
 */
public interface StepCollector {
    enum Stage {
        BEFORE, AFTER
    }

    /**
     * Collects all of the {@link BeforeStories} or {@link AfterStories} steps to execute.
     * 
     * @param candidateSteps
     * @param stage the {@link Stage} of execution
     * @return A List of executable {@link Step}s 
     */
    List<Step> collectBeforeOrAfterStoriesSteps(List<CandidateSteps> candidateSteps, Stage stage);

    /**
     * Collects all of the {@link BeforeStory} or {@link AfterStory} steps to execute.
     * 
     * @param candidateSteps the {@link CandidateSteps}.
     * @param story the {@link Story}.
     * @param stage the {@link Stage} of execution
     * @param givenStory whether {@link Story} is a given story
     * @return A List of executable {@link Step}s 
     */
    List<Step> collectBeforeOrAfterStorySteps(List<CandidateSteps> candidateSteps, Story story, Stage stage, boolean givenStory);

    /**
     * Collects all of the {@link BeforeScenario} or {@link AfterScenario} steps to execute.
     * 
     *
     * @param candidateSteps the {@link org.jbehave.core.steps.CandidateSteps}.
     * @param storyAndScenarioMeta the story and scenario {@link Meta} parameters
     * @param failureOccured whether a failure occured in the scenario execution  @return A List of executable {@link Step}s
     */
    List<Step> collectBeforeOrAfterScenarioSteps(List<CandidateSteps> candidateSteps, Meta storyAndScenarioMeta, Stage stage, boolean failureOccured);

    /**
     * Collects all of the {@link Step}s to execute for a scenario.
     * 
     * @param candidateSteps the {@link CandidateSteps}.
     * @param scenario the {@link Scenario}.
     * @param parameters the parameters.
     * @return A List of executable {@link Step}s 
     */
    List<Step> collectScenarioSteps(List<CandidateSteps> candidateSteps, Scenario scenario, Map<String, String> parameters);
}
