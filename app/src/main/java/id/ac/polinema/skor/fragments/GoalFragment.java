package id.ac.polinema.skor.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import id.ac.polinema.skor.R;
import id.ac.polinema.skor.databinding.FragmentGoalBinding;
import id.ac.polinema.skor.models.GoalScorer;

/**
 * A simple {@link Fragment} subclass.
 */
public class GoalFragment extends Fragment {

    private String requestKey;
    private GoalScorer goalScorer;

    public GoalFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.goalScorer = new GoalScorer();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final FragmentGoalBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_goal, container, false);
        binding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.inputName.getText().toString();
                int minute = Integer.parseInt(binding.inputMinute.getText().toString());
                onSaveClicked(view, name, minute);
            }
        });
        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCancelClicked(view);
            }
        });
        String key = GoalFragmentArgs.fromBundle(getArguments()).getRequestKey();
        Log.e("KEY", key);
        return binding.getRoot();
    }

    public void onSaveClicked(View view, String name, int minute) {
        String requestKey = GoalFragmentArgs.fromBundle(getArguments()).getRequestKey();
        goalScorer.setName(name);
        goalScorer.setMinute(minute);
        Bundle result = new Bundle();
        result.putParcelable("data", goalScorer);
        result.putString("key", requestKey);
        getParentFragmentManager().setFragmentResult("scorer", result);
        Navigation.findNavController(view).navigateUp();
    }

    public void onCancelClicked(View view) {
        Navigation.findNavController(view).navigateUp();
    }
}