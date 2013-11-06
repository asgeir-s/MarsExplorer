Steels Mars Explorer,
=============
using <a href="http://jade.tilab.com/">Jade</a> and <a href="http://simbad.sourceforge.net/">Simbad</a>.

Requirements
-------------
**To make this project run**

Install Java3D into your java jdk directory:
1. <a href="https://java3d.java.net/binary-builds.html">Download Java3D</a>
2. <a href="http://download.java.net/media/java3d/builds/release/1.5.1/README-download">Installation
instruction</a>

**Get vecmath**
3. Download <a href="http://mirrors.ibiblio.org/pub/mirrors/maven/java3d/jars/vecmath-1.3.1
.jar">vecmath-1.3.1.jar</a> and put it in the lib folder

Scenario
-------------
The Mars Explorer scenario was presented by L Steels.

**The objectives are**

 * to explore a distant planet, and in particular,
 * to collect sample of a precious rock
 * the location of the samples is not known in advance, but it is known that they tend to be clustered
 * Agents are limited in the amount of sample they can carry at a given time
 * Obstacles impede motion in some cells


**The model includes**
 * mother ship broadcasts radio signal which weakens with distance. Thus, an agent knows how to return to the mother ship and how far away it is.
 * no map of the area is available – and the agents have insufficient space to store one
 * We are interested in the collaborative best solution which is based on the amount of sample collected in a specified number of time steps.
 * There is no means of contacting other agents directly. The only communication is stigmergic (by modifying the environment itself)