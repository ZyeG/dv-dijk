***Description***
This is a collaborative course project for CSCD58 Computer Network at UTSC. This repo contains code for my part only (part I: routing algorithm: distance vector & link state).

This project examines routing algorithms and protocols in theory (distance vector & link state
algorithm in java emulation) and in practice (BGP and OSPF with mininet).

*PartI*
We started the project with simple routing algorithm implementations: finding shortest path
with distance vector (DV) and Djikstra’s link state (DL). Distance vector information is
wrapped in a packet, scheduled to be sent from source to destination at a random time T
(decided based on the seed set at runtime).

*PartII*
For BGP (each router as an individual AS) and OSPF (all routers in one AS) simulation,
most time was spent on experimenting with various tools and configuring parameters
properly for each tool to observe the routing behavior desired. We started with mininet, since
it was introduced in class and we have worked with it in multiple labs; then integrated it with
the Quagga routing framework suite, so as to observe and compare convergence speed,
throughput and jitter between BGP and OSPF.