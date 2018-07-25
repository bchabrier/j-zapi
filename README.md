# j-zapi
ZAPI Java implementation

This repository has been re-created from https://code.google.com/archive/p/j-zapi, which was stopped at release 0.0.5. 

## j-zapi

Une implementation Java de l'API de la Zibase
http://zodianet.com

Java 1.5 minimum


## Utilisation
Une utilisation simple, voici un exemple:

```java
  // découverte de la zibase sur le réseau local
  ZbNopResponse zbNopResponse = Broadcast.nop();

  if (zbNopResponse == null)
    System.out.println("Zibase non découverte.");
  else {
    System.out.println("ZibaseId       = " + zbNopResponse.getZbHeader().getZibaseId());
    System.out.println("IP de la Zibase= " + zbNopResponse.getInetAddress().getHostAddress());
    Zibase zibase = new Zibase(zbNopResponse.getInetAddress().getHostAddress());

    // allume une lampe (un module fibaro reconnu comme ZA4)
    zibase.sendCommand("A4", ZbAction.ON, ZbProtocol.ZWAVE);

    // allume une lampe (un module ON/OFF Chacon DI-O reconnu comme A1)
    zibase.sendCommand("A1", ZbAction.ON, ZbProtocol.CHACON);

    // lecture de la variable 1 de la Zibase
    int value = zibase.getVariable(1);

    // mise à jour de la variable
    zibase.setVariable(1, value+1);

    // ...
  }
```


## de nombreux exemples
Chaque partie de l'api est illustrée par un exemple que j'essaierai de commenter le plus clairement possible.


## Références
la documentation de Zodianet http://zodianet.com/images/specs/ZAPI1.14.PDF'>http://zodianet.com/images/specs/ZAPI1.14.PDF

Il s'agit de la version 1.14 de la spec.


## Les commandes de la ZAPI
Les commandes suivantes sont implémentées:

```java
NOP
HOST_REGISTERING
HOST_UNREGISTERING
RF_FRAME_SENDING
SCENARIO_LAUNCHING
READ/WRITE VARIABLE
COMMAND_SCRIPT_LAUNCHING
+ récupération des informations fournies par la ZIBASE en http (http://zibase_ip/sensors.xml)
VIRTUAL_PROBE_EVENT
READ/WRITE CALENDAR
```
## Ce que ne couvre pas la librairie java
Cette librairie ne couvre pas les appels à la plateforme tels que: https://zibase.net/m/...
