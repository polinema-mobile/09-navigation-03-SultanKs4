package id.ac.polinema.skor.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.Navigation;

import java.util.ArrayList;

import id.ac.polinema.skor.R;
import id.ac.polinema.skor.databinding.FragmentScoreBinding;
import id.ac.polinema.skor.models.GoalScorer;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScoreFragment extends Fragment {

    public static final String HOME_REQUEST_KEY = "home";
    public static final String AWAY_REQUEST_KEY = "away";
    public static final String SCORER_KEY = "scorer";

    private ArrayList<GoalScorer> homeGoalScorerList;
    private ArrayList<GoalScorer> awayGoalScorerList;

    public ScoreFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.homeGoalScorerList = new ArrayList<>();
        this.awayGoalScorerList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentScoreBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_score, container, false);
        binding.setFragment(this);
        getParentFragmentManager().setFragmentResultListener(SCORER_KEY, this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                GoalScorer goalScorer = result.getParcelable("data");
                if (result.getString("key").equals(HOME_REQUEST_KEY)) {
                    homeGoalScorerList.add(goalScorer);
                } else {
                    awayGoalScorerList.add(goalScorer);
                }
            }
        });
        binding.buttonAddHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddHomeClick(view);
            }
        });
        binding.buttonAddAway.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddAwayClick(view);
            }
        });
        binding.setHomeGoalScorerList(this.homeGoalScorerList);
        binding.setAwayGoalScorerList(this.awayGoalScorerList);

        return binding.getRoot();
    }

    public void onAddHomeClick(View view) {
        ScoreFragmentDirections.GoalScorerAction action = ScoreFragmentDirections.goalScorerAction(HOME_REQUEST_KEY);
        Navigation.findNavController(view).navigate(action);
    }

    public void onAddAwayClick(View view) {
        ScoreFragmentDirections.GoalScorerAction action = ScoreFragmentDirections.goalScorerAction(AWAY_REQUEST_KEY);
        Navigation.findNavController(view).navigate(action);
    }

    public String getHomeScorer() {
        StringBuilder result = new StringBuilder();
        for (GoalScorer g : this.homeGoalScorerList) {
            result.append(g.getName())
                    .append(" ")
                    .append(g.getMinute())
                    .append("\" ");
        }
        return result.toString();
    }

    public String getAwayScorer() {
        StringBuilder result = new StringBuilder();
        for (GoalScorer g : this.awayGoalScorerList) {
            result.append(g.getName())
                    .append(" ")
                    .append(g.getMinute())
                    .append("\" ");
        }
        return result.toString();
    }
}