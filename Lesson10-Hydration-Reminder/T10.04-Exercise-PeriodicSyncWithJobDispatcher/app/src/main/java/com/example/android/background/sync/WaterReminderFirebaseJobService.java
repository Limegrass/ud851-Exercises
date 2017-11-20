/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.background.sync;

import android.app.job.JobParameters;
import android.os.AsyncTask;
import android.os.Build;

public class WaterReminderFirebaseJobService extends com.firebase.jobdispatcher.JobService {
    // DONE (3) WaterReminderFirebaseJobService should extend from JobService

    // DONE (4) Override onStartJob
    private AsyncTask mBackgroundTask;

    @Override
    public boolean onStartJob(final com.firebase.jobdispatcher.JobParameters job) {
        mBackgroundTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                ReminderTasks.executeTask(
                        WaterReminderFirebaseJobService.this,
                        ReminderTasks.ACTION_REMINDER);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    jobFinished(job, false);
                }
                super.onPostExecute(o);
            }
        };
        mBackgroundTask.execute();
        return true;
    }


    // DONE (5) By default, jobs are executed on the main thread, so make an anonymous class extending
        //  AsyncTask called mBackgroundTask.
            // DONE (6) Override doInBackground
                // DONE (7) Use ReminderTasks to execute the new charging reminder task you made, use
                // this service as the context (WaterReminderFirebaseJobService.this) and return null
                // when finished.
            // DONE (8) Override onPostExecute and called jobFinished. Pass the job parameters
            // and false to jobFinished. This will inform the JobManager that your job is done
            // and that you do not want to reschedule the job.

        // DONE (9) Execute the AsyncTask
        // DONE (10) Return true

    // DONE (11) Override onStopJob


    @Override
    public boolean onStopJob(com.firebase.jobdispatcher.JobParameters job) {
        if (mBackgroundTask != null){
            mBackgroundTask.cancel(true);
        }
        return true;
    }

    // DONE (12) If mBackgroundTask is valid, cancel it
        // DONE (13) Return true to signify the job should be retried

}
