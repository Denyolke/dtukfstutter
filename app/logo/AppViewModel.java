package com.example.stutter.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.stutter.data.MockRepository;
import com.example.stutter.model.Level;
import com.example.stutter.model.Question;
import com.example.stutter.model.Topic;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Map;

public class AppViewModel extends ViewModel {

    public MutableLiveData<List<Topic>> topics = new MutableLiveData<>();
    public MutableLiveData<Integer> streak = new MutableLiveData<>(0);
    public MutableLiveData<Integer> totalXP = new MutableLiveData<>(0);

    public MutableLiveData<String> selectedTopicId = new MutableLiveData<>(null);
    public MutableLiveData<Level> selectedLevel = new MutableLiveData<>(null);

    public MutableLiveData<Integer> quizScore = new MutableLiveData<>(0);
    public MutableLiveData<Integer> quizXP = new MutableLiveData<>(0);
    public MutableLiveData<Integer> totalQuestions = new MutableLiveData<>(0);
    public MutableLiveData<Boolean> gameOver = new MutableLiveData<>(false);

    private final Map<String, List<Question>> questionsBank = MockRepository.getQuestionsBank();

    public void load() {
        // Load topics from Firestore instead of regenerating from MockRepository
        loadTopicsFromFirestore();
    }

    /**
     * Load topics from Firestore with actual progress data
     */
    private void loadTopicsFromFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        
        db.collection("topics")
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    List<Topic> loadedTopics = new java.util.ArrayList<>();
                    
                    // If no topics in Firestore, create default ones
                    if (querySnapshot.isEmpty()) {
                        loadedTopics = MockRepository.getTopics();
                        // Save them to Firestore for future loads
                        saveTopicsToFirestore(loadedTopics);
                    } else {
                        // Load from Firestore
                        for (var doc : querySnapshot.getDocuments()) {
                            Topic topic = doc.toObject(Topic.class);
                            if (topic != null) {
                                loadedTopics.add(topic);
                            }
                        }
                    }
                    
                    topics.setValue(loadedTopics);
                })
                .addOnFailureListener(e -> {
                    // If Firestore fails, use mock data
                    System.err.println("Error loading topics: " + e.getMessage());
                    topics.setValue(MockRepository.getTopics());
                });
    }

    /**
     * Save topics to Firestore (called on first load)
     */
    private void saveTopicsToFirestore(List<Topic> topicsToSave) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        
        for (Topic topic : topicsToSave) {
            db.collection("topics").document(topic.id)
                    .set(topic)
                    .addOnFailureListener(e -> {
                        System.err.println("Error saving topic: " + e.getMessage());
                    });
        }
    }

    /**
     * Update topic progress in Firestore after completing a level
     */
    public void updateTopicProgress(String topicId, int newCompletedLessons) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        
        db.collection("topics").document(topicId)
                .update("completedLessons", newCompletedLessons)
                .addOnFailureListener(e -> {
                    System.err.println("Error updating topic progress: " + e.getMessage());
                });
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
