Steels Mars Explorer,
=============
using <a href="http://simbad.sourceforge.net/">Simbad</a>.

Requirements
-------------
Install Java3D into your java jdk directory:
* <a href="https://java3d.java.net/binary-builds.html">Download Java3D</a>
* <a href="http://www.google.no/url?sa=t&rct=j&q=&esrc=s&source=web&cd=2&cad=rja&ved=0CDgQFjAB&url=http%3A%2F%2Fdownload.java.net%2Fmedia%2Fjava3d%2Fbuilds%2Frelease%2F1.5.1%2FREADME-download.html&ei=C1x6UpeDF4nAswaU2IHgBg&usg=AFQjCNGroxuV_CYSfMPtU6_Or-PSXKtTYQ&sig2=u8rvS8kKZA10tp6ITzSt1A&bvm=bv.55980276,d.Yms">Installation
instruction</a>

Get vecmath:
* Download <a href="http://www.findjar.com/jar/java3d/jars/vecmath-1.3.1.jar.html">vecmath-1.3.1.jar</a> and put it in the lib folder

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