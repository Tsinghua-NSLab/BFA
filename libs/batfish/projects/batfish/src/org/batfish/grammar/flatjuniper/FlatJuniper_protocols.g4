parser grammar FlatJuniper_protocols;

import
FlatJuniper_common, FlatJuniper_bgp, FlatJuniper_isis, FlatJuniper_mpls, FlatJuniper_ospf;

options {
   tokenVocab = FlatJuniperLexer;
}

s_protocols
:
   PROTOCOLS s_protocols_tail
;

s_protocols_tail
:
   s_protocols_bgp
   | s_protocols_connections
   | s_protocols_isis
   | s_protocols_mpls
   | s_protocols_null
   | s_protocols_ospf
   | s_protocols_ospf3
;

s_protocols_null
:
   (
      BFD
      | IGMP
      | L2CIRCUIT
      | LACP
      | LDP
      | LLDP
      | MLD
      | MSDP
      | MSTP
      | MVPN
      | NEIGHBOR_DISCOVERY
      | PIM
      | ROUTER_ADVERTISEMENT
      | RSVP
      | VRRP
      | VSTP
   ) s_null_filler
;

