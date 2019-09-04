
Decisions about the views:
-----------------------------

- The submission button appears at the top of the conference display view.

- Broadcasts to authors with submissions and registration is sent just to the authors with submissions and registrations of an especified conference, so the buttons to send the broadcast are inside the conference display view.

- The administrator's functions as assign reviewers, evaluate the submissions and notify the results are inside the display conference view.


Decisions about the implementation:
-------------------------------------
- To delete a topic there must not be any message related to that topic

- When deleting a category all the childs are deleted too (and also the childs of the childs if any). All the categories that were related to any of the category that has been deleted, are reassigned.

- Since it is not specified in the requirements, we have decided that any administrator can edit any conference.

- When a reviewer is assigned to a submission, a report is generated automatically. This report is pending to be completed by the reviewer. Furthermore, a notification message is sent to the assigned reviewers.


Other decisions:
-------------------------------------

- To run the evaluation procedure, all the submissions of the conference must have received a report of every reviewer that has been assigned to it.

- Since the administrator are able to notify the authors whenever they want (as long as the notification deadline is not elapsed), there is a message in blue over the table of submissions that informs the admin when all the submissions with reviewers assigned are evaluated and when there are submissions pending to be evaluated.

- The admin is able to assign submissions, evaluate and notify suring the submission period. However, the admin is not able to do any of those three when the notification period is elapsed.



