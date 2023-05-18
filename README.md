# OBJEKTORIENTIERE KOMPONENTEN-ARCHITEKTUREN SS 2023
## BEARBEITUNG VON ÜBUNG 2 AUFGABE 1.5
Das implementierte Komponentenmodell erfüllt nahezu den gesamten Teilzweig "Lifecycle". Es wurden Komponenten modelliert, implementiert und abschließend als JAR-Datei und Paket bereitgestellt, sodass sie zur Laufzeit deployt werden können.

Der Abschnitt "Interface Specification" wurde mittels des Kommando-Entwurfsmusters umgesetzt. Der Interface-Typ basiert auf Operationen, die hinzugefügt und ausgeführt werden können.

Die Interaktion mit dem Benutzer wurde mithilfe einer synchronen Kommandozeilenimplementierung realisiert. Das System gibt dem Nutzer vor jeder Eingabe alle Eingabemöglichkeiten vor.

Es gibt keine zusätzlichen Funktionen.

Exemplarischer Ablauf:
```
Enter a command (load/start/stop/status/stopHard/exit):
load
Choose between component A and B by typing A/B
A
Mai 18, 2023 10:51:01 PM de.ookaSS23.lzu.controller.ComponentAssembler loadComponentFromJar
INFORMATION: Start Method was found
Mai 18, 2023 10:51:01 PM de.ookaSS23.lzu.controller.ComponentAssembler loadComponentFromJar
INFORMATION: Stop Method was found
Enter a command (load/start/stop/status/stopHard/exit):
start
Choose the number as integer (1,2...) of following components: 
Mai 18, 2023 10:51:05 PM de.ookaSS23.lzu.command.DisplayStatusOperation execute
INFORMATION: Name of component: componentA.jar - State of component: deployed - Id of component: 8132bc18-7d89-4893-b443-c3dce11c8b0e - ThreadPoolSize - 0
Mai 18, 2023 10:51:05 PM de.ookaSS23.lzu.controller.ComponentAssembler executeComponentOperations
INFORMATION: Operation was executed.
1
Mai 18, 2023 10:51:08 PM de.ookaSS23.lzu.controller.ComponentAssembler executeComponentOperations
INFORMATION: Operation was executed.
Mai 18, 2023 10:51:08 PM de.ookaSS23.lzu.controller.ComponentWrapper run
INFORMATION: Component was started.
I am partOne of componentA with uuid e16d84c2-d260-4e6e-a71f-d84748c3dd5d
I am partTwo of componentA with uuid e16d84c2-d260-4e6e-a71f-d84748c3dd5d
Enter a command (load/start/stop/status/stopHard/exit):
stop
Choose the number as integer (1,2...) of following components: 
Mai 18, 2023 10:51:14 PM de.ookaSS23.lzu.command.DisplayStatusOperation execute
INFORMATION: Name of component: componentA.jar - State of component: running - Id of component: 8132bc18-7d89-4893-b443-c3dce11c8b0e - ThreadPoolSize - 1
Mai 18, 2023 10:51:14 PM de.ookaSS23.lzu.controller.ComponentAssembler executeComponentOperations
INFORMATION: Operation was executed.
1
Stopping one and two of ComponentA with ID: e16d84c2-d260-4e6e-a71f-d84748c3dd5d
Mai 18, 2023 10:51:17 PM de.ookaSS23.lzu.controller.ComponentWrapper stop
INFORMATION: Component was stopped.
Mai 18, 2023 10:51:17 PM de.ookaSS23.lzu.controller.ComponentAssembler executeComponentOperations
INFORMATION: Operation was executed.
Enter a command (load/start/stop/status/stopHard/exit):
status
Mai 18, 2023 10:51:22 PM de.ookaSS23.lzu.command.DisplayStatusOperation execute
INFORMATION: Name of component: componentA.jar - State of component: stopped - Id of component: 8132bc18-7d89-4893-b443-c3dce11c8b0e - ThreadPoolSize - 1
Mai 18, 2023 10:51:22 PM de.ookaSS23.lzu.controller.ComponentAssembler executeComponentOperations
INFORMATION: Operation was executed.
Enter a command (load/start/stop/status/stopHard/exit):
exit

Process finished with exit code 0

```
