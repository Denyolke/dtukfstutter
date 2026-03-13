package com.example.stutter.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.stutter.data.MockRepository;
import com.example.stutter.model.Level;
import com.example.stutter.model.Question;
import com.example.stutter.model.Topic;

import java.util.List;
import java.util.Map;

public class AppViewModel extends ViewModel {

    public MutableLiveData<List<Topic>> topics = new MutableLiveData<>();
    public MutableLiveData<Integer> streak = new MutableLiveData<>(7);
    public MutableLiveData<Integer> totalXP = new MutableLiveData<>(0);

    public MutableLiveData<String> selectedTopicId = new MutableLiveData<>(null);
    public MutableLiveData<Level> selectedLevel = new MutableLiveData<>(null);

    public MutableLiveData<Integer> quizScore = new MutableLiveData<>(0);
    public MutableLiveData<Integer> quizXP = new MutableLiveData<>(0);
    public MutableLiveData<Integer> totalQuestions = new MutableLiveData<>(0);
    public MutableLiveData<Boolean> gameOver = new MutableLiveData<>(false);

    private final Map<String, List<Question>> questionsBank = MockRepository.getQuestionsBank();

    public void load() {
        topics.setValue(MockRepository.getTopics());
    }

    public List<Question> getQuestionsForSelectedTopic() {
        String id = selectedTopicId.getValue();
        if (id == null) return null;
        return questionsBank.get(id);
    }

    public List<Question> getQuestionsForLevel(Level level) {
        // Get questions for specific level
        // For now, return all questions from the topic
        String id = selectedTopicId.getValue();
        if (id == null) return null;
        return questionsBank.get(id);
    }

    public void resetQuiz() {
        selectedTopicId.setValue(null);
        selectedLevel.setValue(null);
        quizScore.setValue(0);
        quizXP.setValue(0);
        totalQuestions.setValue(0);
        gameOver.setValue(false);
    }
}
