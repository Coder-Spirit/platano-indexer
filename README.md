Platano Indexer
===============

Platano Indexer is a Free Software text indexer/searcher (MIT License) written
in Java using as a base the JBoss Application Server 7.

Platano Indexer is designed to allow multiple storage backends, but at this
moment only MongoDB is supported.

## A little bit of history

Platano Indexer was an internal project of Bananity (http://www.bananity.com),
a recently created social network in Catalonia, Spain.

At this moment, it's main developer is Andreu Correa Casablanca (castarco), who
is working as a freelance.

## Basic Guide

1. Install JBoss Application Server on your machine. It's recommended to use the
community version (7.1.1 Final).
2. Install MongoDB on your machine. Platano Indexer was tested on Mongo 2.2 and
Mongo 2.4. It's a good idea to use the 10gen repositories if you use Debian or
Ubuntu.
3. Go to src/main/resources/ and edit LOCAL_conf.properties to configure your
Platano Indexer server.
4. Type the command `make local_deploy`. This command installs Platano Indexer as
an application in your JBoss Application Server.
5. Type the command `make jboss_start`. This command starts Jboss and Platano
Indexer.