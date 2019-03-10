import {Resource} from '../shared/resource';

/*
"jobId": "b1b8ca8f-0f6d-46d4-8d5c-80f001d0d82e",
        "jobGroup": "job.group.run",
        "lastExecutionDate": "2019-03-09T22:20:24.109",
        "createdDate": null,
        "nextExceutionDate": null,
        "data": {
            "applicationRunId": 395,
            "collectionId": 300
        }
 */
export class RunningTaskResponse extends Resource {
  jobId: string;
  jobGroup: string;
  lastExecutionDate: Date;
  nextExecutionDate: Date;
  state: string;
}
