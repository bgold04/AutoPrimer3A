 ## CURRENT RELEASE

This project, AutoPrimer3A, is based on the original work of David Parry, who created AutoPrimer3. All original code and documentation remain Copyright © David Parry under the GPLv3 license.

AutoPrimer3A continues as a separate and independently maintained project.


The latest version of AutoPrimer3A updates the code to Java1.8.0_421 and uses mysql-connector-j-9.0.0 to interrogate the MariaDB at UCSC. It provides a facility to specify coordinates in bed format or names for target genes when designing primers or probes 

---

# AutoPrimer3

AutoPrimer3 retrieves gene information, DNA sequences and SNP information from the [UCSC genome browser](http://genome.ucsc.edu/) and uses [primer3](http://primer3.sourceforge.net/) to automatically design primers to genes or genomic coordinate targets. 

Primers may be designed using information from any of the UCSC hosted genomes and primers can be made to avoid overlapping SNPs for genomes where SNP databases are available. 

An instructions PDF can be viewed by selecting the 'Help' menu item from the application or by visiting [here](https://github.com/gantzgraf/autoprimer3/blob/master/src/com/github/autoprimer3/instructions.pdf).

Get the latest release [here](https://github.com/gantzgraf/autoprimer3/releases/latest).

### CREDIT

AutoPrimer3 was written by David A. Parry (david.parry@igmm.ed.ac.uk)

If you use this program to generate primers used in a publication, please cite the URL 'https://github.com/david-a-parry/autoprimer3'.

More information about Primer3, the primer design tool employed by AutoPrimer3, is available at http://primer3.sourceforge.net.

### LICENSE

AutoPrimer3 is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
