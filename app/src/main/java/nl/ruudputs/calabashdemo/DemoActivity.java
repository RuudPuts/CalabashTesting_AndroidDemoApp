package nl.ruudputs.calabashdemo;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class DemoActivity extends Activity {

    private InputFragment mInputFragment;
    private ResultFragment mResultFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        setTitle("Input");
        getActionBar().setHomeButtonEnabled(true);

        if (savedInstanceState == null) {
            mInputFragment = new InputFragment();
            getFragmentManager().beginTransaction()
                    .add(R.id.container, mInputFragment)
                    .commit();
        }
    }

    public void showResult() {
        setTitle("Result");
        String text = mInputFragment.textfield.getText().toString();
        mResultFragment = new ResultFragment();

        Bundle arguments = new Bundle();
        arguments.putString("text", text);
        mResultFragment.setArguments(arguments);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, mResultFragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class InputFragment extends Fragment implements View.OnClickListener {

        public Button nextButton;
        public EditText textfield;

        private View.OnClickListener mButtonListener;

        public InputFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_input, container, false);
            nextButton = (Button) rootView.findViewById(R.id.input_next_button);
            nextButton.setOnClickListener(this);
            textfield = (EditText) rootView.findViewById(R.id.input_textfield);
            return rootView;
        }

        @Override
        public void onClick(View v) {
            ((DemoActivity)getActivity()).showResult();
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class ResultFragment extends Fragment {

        public TextView label;
        private String mTextToDisplay;

        public ResultFragment() {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_result, container, false);
            label = (TextView) rootView.findViewById(R.id.result_label);
            label.setText(getArguments().getString("text"));
            return rootView;
        }
    }
}
