# Reproducer

--- Working in Progress ----

from a clean state(fresh disk)

1- post messages to queue
2- consume and reject messages
3- messages should go to the right deadletter

3- Add new queue


# Clean everything to test again
oc delete all -l template=amq63 && oc delete pvc broker-amq-claim