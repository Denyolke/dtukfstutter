package com.example.stutter.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.stutter.data.MockRepository;
import com.example.stutter.model.Question;
import com.example.stutter.model.Topic;

import java.util.List;
import java.util.Map;

public class AppViewModel extends ViewModel {

    public MutableLiveData<List<Topic>> topics = new MutableLiveData<>();
    public MutableLiveData<Integer> streak = new MutableLiveData<>(7);
    public MutableLiveData<Integer> totalXP = new MutableLiveData<>(450);

    public MutableLiveData<String> selectedTopicId = new MutableLiveData<>(null);

    public MutableLiveData<Integer> quizScore = new MutableLiveData<>(0);
    public MutableLiveData<Integer> quizXP = new MutableLiveData<>(0);

    private final Map<String, List<Question>> questionsBank = MockRepository.getQuestionsBank();

    public void load() {
        topics.setValue(MockRepository.getTopics());
    }

    public List<Question> getQuestionsForSelectedTopic() {
        String id = selectedTopicId.getValue();
        if (id == null) return null;
        return questionsBank.get(id);
    }

    public void resetQuiz() {
        selectedTopicId.setValue(null);
        quizScore.setValue(0);
        quizXP.setValue(0);
    }
}
