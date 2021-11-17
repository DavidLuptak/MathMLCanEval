MathMLCanEval – A web application for evaluating the [MathML Canonicalizer][mathmlcan]
======================================================================================
[![CircleCI](https://circleci.com/gh/MIR-MU/MathMLCanEval/tree/master.svg?style=shield)][ci]

 [ci]: https://circleci.com/gh/MIR-MU/MathMLCanEval/tree/master (CircleCI)

The development of the [MathML Canonicalizer][mathmlcan] requires proper
testing. To meet these needs, we have developed [MathMLCanEval][] – a web
application with the aim to create a large test database of mathematical
formulae covering the entire MathML 3.0 Presentation markup. We use the
database to evaluate the correctness and effectiveness of the MathML
Canonicalizer.

 [mathmlcan]: https://github.com/MIR-MU/MathMLCan
 [mathmlcaneval]: https://mir.fi.muni.cz/mathml-normalization/#mathml-canonicalizer-evaluation


How to build the project
========================

A possible way to reproduce the code is to run VM with Debian distro. We use
[Stratus.FI](https://stratus.fi.muni.cz/) for this purpose. Follow the instructions
to create your instance:

1. Log in with faculty login and password
2. Add your public SSH key (Settings > Auth)
3. Instantiate VM template (Templates > VMs)
   - Select `Debian 11 [CVTFI]`
   - Increase Disk size to at least 8 GB
4. SSH to the VM (from inside FI network, e.g. VPN FI)
   - `ssh root@<IP>`, where `<IP>` is VM's IP
5. `git clone https://github.com/DavidLuptak/MathMLCanEval.git`
6. `cd MathMLCanEval`
7. `git checkout v-2docker` (`v-2docker` should be the default branch, though)
8. `./init.sh`
9. Open `<IP>:9080`, where `<IP>` is VM's IP, in your browser and have fun!

Citing MathMLCanEval
====================
Text
----
FORMÁNEK, David, Martin LÍŠKA, Michal RŮŽIČKA and Petr SOJKA. Normalization of
Digital Mathematics Library Content. *CEUR Workshop Proceedings,* Aachen, 2012,
vol. 921, October, pp. 91–103, ISSN 1613-0073. 

BibTeX
------
``` bib
@inproceedings{ceur:921:05,
      title = {Normalization of Digital Mathematics Library Content},
     author = {David Form{\'a}nek and Martin L{\'\i}{\v s}ka
	       and Michal R{\r u}{\v z}i{\v c}ka and Petr Sojka},
      pages = {91--103},
        url = {http://ceur-ws.org/Vol-921/wip-05.pdf},
   crossref = {ceur:921},
}

@proceedings{ceur:921,
  booktitle = {24th OpenMath Workshop, 7th Workshop on Mathematical User
	       Interfaces (MathUI), and Intelligent Computer Mathematics Work
               in Progress},
      title = {Joint Proceedings of the 24th OpenMath Workshop, the 7th
	       Workshop on Mathematical User Interfaces (MathUI), and the Work
	       in Progress Section of the Conference on Intelligent Computer
               Mathematics},
       year = 2012,
     editor = {James Davenport and Johan Jeuring and Christoph Lange
               and Paul Libbrecht},
     number = 921,
     series = {CEUR Workshop Proceedings},
    address = {Aachen},
       issn = {1613-0073},
        url = {http://ceur-ws.org/Vol-921/},
      venue = {Bremen, Germany},
  eventdate = {2012-07-09/2012-07-13},
}
```
