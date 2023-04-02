package com.gedalias.controledeprojeto.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.gedalias.controledeprojeto.R;
import com.gedalias.controledeprojeto.domain.Project;
import com.gedalias.controledeprojeto.domain.ProjectType;
import com.gedalias.controledeprojeto.domain.TimeType;
import com.gedalias.controledeprojeto.util.AppFactory;
import com.gedalias.controledeprojeto.util.Notification;
import com.gedalias.controledeprojeto.util.impl.AppFactoryImpl;


@RequiresApi(api = Build.VERSION_CODES.N)
public class CreateProjectActivity extends BaseActivity {

    private EditText name;
    private EditText description;
    private RadioGroup typeGroup;
    private Spinner optTime;
    private EditText duration;
    private CheckBox alreadyFinished;
    private int projectId;

    private Notification notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.create_new_project_title);
        setContentView(R.layout.activity_create_project);
        AppFactory factory = new AppFactoryImpl(this);

        notification = factory.createNotification();

        name = (EditText) findViewById(R.id.nameInput);
        description = (EditText) findViewById(R.id.descriptionInput);
        typeGroup = (RadioGroup) findViewById(R.id.typeRadionBtn);
        optTime = (Spinner) findViewById(R.id.optTimeSpin);
        duration = (EditText) findViewById(R.id.durationTimeInput);
        alreadyFinished = (CheckBox) findViewById(R.id.alreadyCheckbox);

        if(hasProject()) {
            Project project = getProject();
            projectId = this.getIntent().getIntExtra("projectId", -1);
            name.setText(project.getName());
            description.setText(project.getDescription());
            duration.setText(String.valueOf(project.getDuration()));
            alreadyFinished.setChecked(project.isAlreadyFinished());
            optTime.setSelection(getIndex(optTime, project.getTimeOption().getTimeOption()));

            RadioButton radioButton = (RadioButton) (
                    (project.getType() == ProjectType.PERSONAL)?
                            findViewById(R.id.opt_personal) :
                            findViewById(R.id.opt_work)
            );
            radioButton.setChecked(true);
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_creation_update, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case CLEAN_OPTION:
                name.setText("");
                description.setText("");
                optTime.clearFocus();
                duration.setText("");
                alreadyFinished.setSelected(false);

                optTime.setSelection(0);

                RadioButton radioButton = (RadioButton) findViewById(R.id.opt_work);
                radioButton.setChecked(true);

                final int titleId = hasProject()? R.string.updated_project_title : R.string.create_project_title;
                notification.success(getString(titleId), getString(R.string.clean_notification));
                break;
            case SAVE_OPTION:
                final boolean validInputs = validateFieldAndInputFocus(name, description, duration);

                if(!validInputs) {
                    final String messageInvalidField = getString(R.string.create_field_empty);
                    notification.error(getString(R.string.create_project_title), messageInvalidField);
                    Log.e("onSaveProject", messageInvalidField);
                } else {
                    RadioButton typeProject = (RadioButton) findViewById(typeGroup.getCheckedRadioButtonId());
                    TextView timeTypeOption = (TextView) optTime.getSelectedView();

                    Project savedProject = new Project(
                            name.getText().toString(),
                            description.getText().toString(),
                            ProjectType.of(typeProject.getText().toString(), this),
                            TimeType.of(timeTypeOption.getText().toString(), this),
                            Integer.parseInt(duration.getText().toString()),
                            alreadyFinished.isSelected()
                    );

                    Intent intent = new Intent();
                    intent.putExtra("project", savedProject);
                    intent.putExtra("projectId", projectId);

                    setResult(RESULT_OK, intent);
                    finish();
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private Project getProject() {
        return (Project) this.getIntent().getExtras().getSerializable("project");
    }

    private boolean hasProject() {
        try {
            return !this.getIntent().getExtras().isEmpty();
        } catch (NullPointerException nex) {
            return false;
        }
    }

    private int getIndex(Spinner spinner, int myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(getString(myString))){
                return i;
            }
        }
        return 0;
    }

    private boolean validateFieldAndInputFocus(EditText ...fields){
        for(EditText field : fields) {
            if (isEmptyInput(field)) {
                field.requestFocus();
                return false;
            }
        }
        return true;
    }

    private boolean isEmptyInput(EditText editText) {
        return editText.getText().toString().isEmpty();
    }

    private final int CLEAN_OPTION = R.id.clean_option;
    private final int SAVE_OPTION = R.id.save_option;
}