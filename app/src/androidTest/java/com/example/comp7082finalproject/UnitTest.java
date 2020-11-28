package com.example.comp7082finalproject;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.rule.ActivityTestRule;

import com.example.comp7082finalproject.View.MainActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class UnitTest {
    @Rule
    public ActivityTestRule<MainActivity> rule  = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void ensureListViewIsPresent()  {
        MainActivity activity = rule.getActivity();
        View viewById = activity.findViewById(R.id.recyclerView);
        assertThat(viewById, not( nullValue() ));

        assertThat(viewById, instanceOf(RecyclerView.class));


        FloatingActionButton create = activity.findViewById(R.id.fab);
        assertThat(create, not( nullValue() ));
        assertThat(create, instanceOf(FloatingActionButton.class));

    }

}
