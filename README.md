# Reproducer

From a clean state(fresh disk)

1. Post messages to the topic **test.worker**
2. Consume and reject messages from queue **sub.gtest.test.workorder**
3. Messages should go to the right wrong dead letter queue (**ActiveMQ.DLQ**)
4. Modify **openshift-activemq.xml** by adding a new queue
5. Push this change to the git repository (you need to clone this repo for this)
6. Build a new Image
7. Redo steps 1 & 2
8. Now messages should go to the right queue **sub.gtest.test.workorder.DLQ**



# Create Project 
oc new-app --template=amq63 --param=GIT_URI=https://gitlab.com/openshift-samples/amq63.git

# Clean everything to test again
oc delete all -l template=amq63 && oc delete pvc broker-amq-claim

# Import AMQ Image 
 oc import-image registry.access.redhat.com/jboss-amq-6/amq63-openshift:latest --confirm
