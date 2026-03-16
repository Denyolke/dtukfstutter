package com.example.stutter.data;

import com.example.stutter.model.DailyQuest;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class QuestManager {

    // ── Quest pool ────────────────────────────────────────────────────────────

    public static List<DailyQuest> buildQuestPool(String dateKey) {
        List<DailyQuest> pool = new ArrayList<>();

        pool.add(new DailyQuest("quest_1", "Level Grinder",
                "Complete 3 levels today",
                3, 30, "COMPLETE_QUIZZES", dateKey));

        pool.add(new DailyQuest("quest_2", "XP Hunter",
                "Earn 50 XP today",
                50, 40, "EARN_XP", dateKey));

        pool.add(new DailyQuest("quest_3", "Sharp Mind",
                "Answer 15 questions correctly",
                15, 25, "CORRECT_ANSWERS", dateKey));

        pool.add(new DailyQuest("quest_4", "Course Finisher",
                "Complete 1 full topic",
                1, 60, "COMPLETE_TOPIC", dateKey));

        pool.add(new DailyQuest("quest_5", "Streak Keeper",
                "Maintain a 2-day login streak",
                2, 20, "LOGIN_STREAK", dateKey));

        pool.add(new DailyQuest("quest_6", "Perfectionist",
                "Finish a level with a perfect score",
                1, 50, "PERFECT_SCORE", dateKey));

        return pool;
    }

    // ── Date helper ───────────────────────────────────────────────────────────

    public static String todayKey() {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    }

    // ── Pick 2 random quests ──────────────────────────────────────────────────

    public static List<DailyQuest> pickTwoRandom(String dateKey) {
        List<DailyQuest> pool = buildQuestPool(dateKey);
        Collections.shuffle(pool);
        return new ArrayList<>(pool.subList(0, 2));
    }

    // ── Interfaces ────────────────────────────────────────────────────────────

    public interface OnQuestsLoadedListener {
        void onLoaded(List<DailyQuest> quests);
        void onError(String error);
    }

    public interface OnQuestProgressListener {
        void onQuestCompleted(String questId, int coinsEarned);
        void onError(String error);
    }

    // ── Firestore: load or generate ───────────────────────────────────────────

    public static void loadOrGenerateDailyQuests(String uid, OnQuestsLoadedListener listener) {
        String today = todayKey();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users").document(uid)
                .collection("dailyQuests")
                .whereEqualTo("dateKey", today)
                .get()
                .addOnSuccessListener(snapshot -> {
                    if (!snapshot.isEmpty()) {
                        List<DailyQuest> quests = new ArrayList<>();
                        for (var doc : snapshot.getDocuments()) {
                            DailyQuest q = doc.toObject(DailyQuest.class);
                            if (q != null) quests.add(q);
                        }
                        listener.onLoaded(quests);
                    } else {
                        List<DailyQuest> newQuests = pickTwoRandom(today);
                        saveQuests(uid, newQuests, new SaveCallback() {
                            @Override public void onSaved()             { listener.onLoaded(newQuests); }
                            @Override public void onError(String error) { listener.onError(error); }
                        });
                    }
                })
                .addOnFailureListener(e -> listener.onError(e.getMessage()));
    }

    // ── Firestore: increment progress ─────────────────────────────────────────

    public static void incrementQuestProgress(String uid, String questType, int amount,
                                              OnQuestProgressListener listener) {
        String today = todayKey();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users").document(uid)
                .collection("dailyQuests")
                .whereEqualTo("dateKey", today)
                .whereEqualTo("type", questType)
                .get()
                .addOnSuccessListener(snapshot -> {
                    for (var doc : snapshot.getDocuments()) {
                        DailyQuest q = doc.toObject(DailyQuest.class);
                        if (q == null || q.completed) continue;

                        int newProgress  = Math.min(q.progress + amount, q.target);
                        boolean nowDone  = newProgress >= q.target;

                        doc.getReference().update(
                                "progress",  newProgress,
                                "completed", nowDone
                        );

                        if (nowDone && listener != null) {
                            listener.onQuestCompleted(q.questId, q.rewardCoins);
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    if (listener != null) listener.onError(e.getMessage());
                });
    }

    // ── Firestore: save helper ────────────────────────────────────────────────

    private interface SaveCallback {
        void onSaved();
        void onError(String error);
    }

    private static void saveQuests(String uid, List<DailyQuest> quests, SaveCallback callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final int[]     remaining = {quests.size()};
        final boolean[] failed    = {false};

        for (DailyQuest q : quests) {
            db.collection("users").document(uid)
                    .collection("dailyQuests")
                    .document(q.questId)
                    .set(q)
                    .addOnSuccessListener(unused -> {
                        remaining[0]--;
                        if (remaining[0] == 0 && !failed[0]) callback.onSaved();
                    })
                    .addOnFailureListener(e -> {
                        if (!failed[0]) {
                            failed[0] = true;
                            callback.onError(e.getMessage());
                        }
                    });
        }
    }
}