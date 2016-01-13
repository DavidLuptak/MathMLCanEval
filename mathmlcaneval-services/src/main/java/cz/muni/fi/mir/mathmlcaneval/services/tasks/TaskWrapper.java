/*
 * Copyright 2016 MIR@MU.
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
package cz.muni.fi.mir.mathmlcaneval.services.tasks;

import java.util.concurrent.Future;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class TaskWrapper
{
    private Future<Task> future;
    private Task task;

    public TaskWrapper()
    {
    }

    public TaskWrapper(Future<Task> future, Task task)
    {
        this.future = future;
        this.task = task;
    }

    public Future<Task> getFuture()
    {
        return future;
    }

    public void setFuture(Future<Task> future)
    {
        this.future = future;
    }

    public Task getTask()
    {
        return task;
    }

    public void setTask(Task task)
    {
        this.task = task;
    }

}
