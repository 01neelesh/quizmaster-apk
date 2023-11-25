package com.example.quizmaster;

import static androidx.databinding.adapters.TextViewBindingAdapter.setText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;


import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.quizmaster.databinding.ActivityMainBinding;
import com.example.quizmaster.model.Questions;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private int currentQuestionIndex = 0;
    private Questions[] questionBank;
    private View popupView;
    private static final int[] THRESHOLDS = {20, 40, 60, 80, 90};
    private static final String[] RESULTS = {
            "Healthy",
            "Normal",
            "Moderate",
            "Critical",
            "Hazardous",
            "Dangerous"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
//        binding = DataBindingUtil.setContentView(this, R.layout.popup_result);
        popupView = LayoutInflater.from(this).inflate(R.layout.popup_result, null);

        // Initialize the question bank
        questionBank = initializeQuestions();

        // Display the first question
        displayQuestion(currentQuestionIndex);

        binding.YesBtn.setOnClickListener(view -> onAnswerClick(true));
        binding.noBtn.setOnClickListener(view -> onAnswerClick(false));
        binding.PreviousBtn.setOnClickListener(view -> showPreviousQuestion());
        binding.nextBtn.setOnClickListener(view -> showNextQuestion());

    }

    private void displayQuestion(int index) {
        binding.QuestionText.setText(getString(questionBank[index].getAnsewerId()));
    }

    private void onAnswerClick(boolean answer) {
        questionBank[currentQuestionIndex].setUserResponse(answer);
        showNextQuestion();  // Move to the next question
    }

    private void showNextQuestion() {
        if (currentQuestionIndex < questionBank.length - 1) {
            currentQuestionIndex++;
            displayQuestion(currentQuestionIndex);
        } else {
            calculateResult();
        }
    }

    private void showPreviousQuestion() {
        if (currentQuestionIndex > 0) {
            currentQuestionIndex--;
            displayQuestion(currentQuestionIndex);
        }
    }

    private Questions[] initializeQuestions() {
        int[] questionResId = new int[]{
                R.string.Query1, R.string.Query2, R.string.Query3, R.string.Query4, R.string.Query5,
                R.string.Query6, R.string.Query7, R.string.Query8, R.string.Query9, R.string.Query10,
                R.string.Query11, R.string.Query12, R.string.Query13, R.string.Query14, R.string.Query15,
                R.string.Query16, R.string.Query17, R.string.Query18, R.string.Query19, R.string.Query20,
                R.string.Query21, R.string.Query22, R.string.Query23, R.string.Query24, R.string.Query25,
        };

        Questions[] questions = new Questions[questionResId.length];
        for (int i = 0; i < questionResId.length; i++) {
            questions[i] = new Questions(questionResId[i], false);
        }

        return questions;
    }

    private void calculateResult() {
        int totalQueations = questionBank.length;
        int answeredYes = 0;

        for (Questions questions : questionBank) {
            if (questions.getUserResponse()) {
                answeredYes++;
            }
        }
        double percentage = (answeredYes / (double) totalQueations) * 100;
        String result = determineResult(percentage);
        displayResult(result);
    }

    private void displayResultWithPopup(String result) {
        TextView resutlTextView = popupView.findViewById(R.id.resulttext);
        resutlTextView.setText(result);
        int color = getColorForResult(result);
        resutlTextView.setTextColor(color);

        PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.showAtLocation(binding.getRoot(), Gravity.CENTER, 0, 0);

    }

    private int getColorForResult(String result) {
        switch (result) {
            case "Healthy":
                return getResources().getColor(R.color.green);
            case "Normal":
                return getResources().getColor(R.color.Blue);

            case "Moderate":
                return getResources().getColor(R.color.Maroon);
            case "Critical":
                return getResources().getColor(R.color.Maroon);
            case "Hazardous":
                return getResources().getColor(R.color.red);
            case "Dangerous":
                return getResources().getColor(R.color.red);
            default:
                return getResources().getColor(R.color.black);
        }
    }

    private String determineResult(double percentage) {
        for (int i = 0; i < THRESHOLDS.length; i++) {
            if (percentage < THRESHOLDS[i]) {
                return RESULTS[i];
            }
        }
        return RESULTS[RESULTS.length - 1];  // If percentage is above the highest threshold
    }

    private void displayResult(String result) {
        displayResultWithPopup(result);

    }
}
