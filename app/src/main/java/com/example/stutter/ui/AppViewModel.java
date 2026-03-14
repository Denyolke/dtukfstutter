package com.example.stutter.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.stutter.data.MockRepository;
import com.example.stutter.model.Level;
import com.example.stutter.model.Question;
import com.example.stutter.model.Topic;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
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

    private final Map<String, Map<Integer, List<Question>>> questionsBank =
            MockRepository.getQuestionsBankByTopicAndLevel();

    public interface SaveProgressCallback {
        void onSuccess();
        void onFailure(String errorMessage);
    }

    public void load() {
        loadTopicsForCurrentUser();
    }

    private void loadTopicsForCurrentUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user == null) {
            topics.setValue(MockRepository.getTopics());
            return;
        }

        String uid = user.getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        List<Topic> defaultTopics = MockRepository.getTopics();

        db.collection("users")
                .document(uid)
                .collection("topicProgress")
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    Map<String, Topic> progressMap = new HashMap<>();

                    for (var doc : querySnapshot.getDocuments()) {
                        String topicId = doc.getId();

                        Long completedLessonsObj = doc.getLong("completedLessons");
                        Boolean completedObj = doc.getBoolean("completed");

                        int completedLessons = completedLessonsObj != null
                                ? completedLessonsObj.intValue()
                                : 0;

                        boolean completed = completedObj != null && completedObj;

                        Topic progressTopic = new Topic();
                        progressTopic.id = topicId;
                        progressTopic.completedLessons = completedLessons;
                        progressTopic.completed = completed;

                        progressMap.put(topicId, progressTopic);
                    }

                    List<Topic> mergedTopics = new ArrayList<>();

                    for (Topic baseTopic : defaultTopics) {
                        Topic merged = new Topic(
                                baseTopic.id,
                                baseTopic.title,
                                baseTopic.description,
                                baseTopic.icon,
                                false,
                                0,
                                10
                        );

                        if (progressMap.containsKey(baseTopic.id)) {
                            Topic saved = progressMap.get(baseTopic.id);
                            merged.completedLessons = saved.completedLessons;
                            merged.completed = saved.completed;
                        }

                        merged.totalLessons = 10;
                        mergedTopics.add(merged);
                    }

                    topics.setValue(mergedTopics);

                    if (querySnapshot.isEmpty()) {
                        createInitialTopicProgressForUser(uid, defaultTopics);
                    }
                })
                .addOnFailureListener(e -> {
                    System.err.println("Error loading user topic progress: " + e.getMessage());
                    topics.setValue(defaultTopics);
                });
    }

    private void createInitialTopicProgressForUser(String uid, List<Topic> defaultTopics) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        for (Topic topic : defaultTopics) {
            Map<String, Object> progressData = new HashMap<>();
            progressData.put("completedLessons", 0);
            progressData.put("completed", false);

            db.collection("users")
                    .document(uid)
                    .collection("topicProgress")
                    .document(topic.id)
                    .set(progressData)
                    .addOnSuccessListener(unused ->
                            System.out.println("Created initial topic progress for topic " + topic.id))
                    .addOnFailureListener(e ->
                            System.err.println("Error creating initial progress for topic "
                                    + topic.id + ": " + e.getMessage()));
        }
    }

    public void updateTopicProgress(String topicId, int newCompletedLessons, SaveProgressCallback callback) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user == null) {
            if (callback != null) callback.onFailure("No logged in user.");
            return;
        }

        String uid = user.getUid();
        boolean isCompleted = newCompletedLessons >= 10;

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> progressData = new HashMap<>();
        progressData.put("completedLessons", newCompletedLessons);
        progressData.put("completed", isCompleted);

        db.collection("users")
                .document(uid)
                .collection("topicProgress")
                .document(topicId)
                .set(progressData)
                .addOnSuccessListener(unused -> {
                    System.out.println("TOPIC PROGRESS SAVED: " + topicId + " -> " + newCompletedLessons);

                    List<Topic> currentTopics = topics.getValue();
                    if (currentTopics != null) {
                        for (Topic topic : currentTopics) {
                            if (topic.id.equals(topicId)) {
                                topic.completedLessons = newCompletedLessons;
                                topic.completed = isCompleted;
                                break;
                            }
                        }
                        topics.setValue(currentTopics);
                    }

                    if (callback != null) callback.onSuccess();
                })
                .addOnFailureListener(e -> {
                    System.err.println("FAILED TO SAVE TOPIC PROGRESS: " + e.getMessage());
                    if (callback != null) callback.onFailure(e.getMessage());
                });
    }

    public List<Level> getLevelsForSelectedTopic() {
        String topicId = selectedTopicId.getValue();
        List<Topic> currentTopics = topics.getValue();

        if (topicId == null || currentTopics == null) return new ArrayList<>();

        for (Topic topic : currentTopics) {
            if (topic.id.equals(topicId)) {
                return MockRepository.getLevelsForTopic(topicId, topic.completedLessons);
            }
        }

        return new ArrayList<>();
    }

    public List<Question> getQuestionsForSelectedLevel() {
        String topicId = selectedTopicId.getValue();
        Level level = selectedLevel.getValue();

        if (topicId == null || level == null) return null;

        Map<Integer, List<Question>> topicQuestions = questionsBank.get(topicId);
        if (topicQuestions == null) return null;

        return topicQuestions.get(level.levelNumber);
    }

    public void reloadTopicsFromFirestore() {
        loadTopicsForCurrentUser();
    }

    public void resetQuizSessionOnly() {
        selectedLevel.setValue(null);
        quizScore.setValue(0);
        quizXP.setValue(0);
        totalQuestions.setValue(0);
        gameOver.setValue(false);
    }

    public void resetAllQuizState() {
        selectedTopicId.setValue(null);
        selectedLevel.setValue(null);
        quizScore.setValue(0);
        quizXP.setValue(0);
        totalQuestions.setValue(0);
        gameOver.setValue(false);
    }
}