parser grammar FlatJuniper_interfaces;

import FlatJuniper_common;

options {
   tokenVocab = FlatJuniperLexer;
}

brt_filter
:
   filter
;

brt_interface_mode
:
   INTERFACE_MODE interface_mode
;

brt_vlan_id_list
:
   VLAN_ID_LIST DEC
;

direction
:
   INPUT
   | INPUT_LIST
   | OUTPUT
   | OUTPUT_LIST
;

famt_bridge
:
   BRIDGE famt_bridge_tail
;

famt_bridge_tail
:
// intentional blank

   | brt_filter
   | brt_interface_mode
   | brt_vlan_id_list
;

famt_ccc
:
   CCC s_null_filler
;

famt_inet
:
   INET famt_inet_tail
;

famt_inet_tail
:
// intentional blank

   | ifamt_address
   | ifamt_apply_groups
   | ifamt_apply_groups_except
   | ifamt_filter
   | ifamt_mtu
   | ifamt_no_redirects
   | ifamt_null
   | ifamt_rpf_check
;

famt_inet6
:
   INET6 s_null_filler
;

famt_iso
:
   ISO famt_iso_tail
;

famt_iso_tail
:
// intentional blank

   | isofamt_address
   | isofamt_mtu
;

famt_mpls
:
   MPLS famt_mpls_tail
;

famt_mpls_tail
:
// intentional blank alternative

   | mfamt_filter
   | mfamt_maximum_labels
   | mfamt_mtu
;

filter
:
   FILTER filter_tail
;

filter_tail
:
// intentional blank

   | ft_direction
;

ft_direction
:
   direction name = variable
;

ifamat_arp
:
   ARP IP_ADDRESS
   (
      MAC
      | MULTICAST_MAC
   ) MAC_ADDRESS
;

ifamat_master_only
:
   MASTER_ONLY
;

ifamat_preferred
:
   PREFERRED
;

ifamat_primary
:
   PRIMARY
;

ifamat_vrrp_group
:
   VRRP_GROUP
   (
      name = variable
      | WILDCARD
   ) ifamat_vrrp_group_tail
;

ifamat_vrrp_group_tail
:
   ivrrpt_accept_data
   | ivrrpt_advertise_interval
   | ivrrpt_authentication_type
   | ivrrpt_preempt
   | ivrrpt_priority
   | ivrrpt_track
   | ivrrpt_virtual_address
;

ifamt_address
:
   ADDRESS
   (
      IP_PREFIX
      | WILDCARD
   ) ifamt_address_tail?
;

ifamt_address_tail
:
   ifamat_arp
   | ifamat_master_only
   | ifamat_preferred
   | ifamat_primary
   | ifamat_vrrp_group
;

ifamt_apply_groups
:
   s_apply_groups
;

ifamt_apply_groups_except
:
   s_apply_groups_except
;

ifamt_filter
:
   filter
;

ifamt_mtu
:
   it_mtu
;

ifamt_no_redirects
:
   NO_REDIRECTS
;

ifamt_null
:
   (
      POLICER
      | SAMPLING
      | SERVICE
      | TARGETED_BROADCAST
   ) s_null_filler
;

ifamt_rpf_check
:
   RPF_CHECK FAIL_FILTER name = variable
;

interface_mode
:
   TRUNK
;

intt_apply_groups
:
   s_apply_groups
;

intt_named
:
   (
      WILDCARD
      | name = VARIABLE
   ) intt_named_tail
;

intt_named_tail
:
   it_common
   | it_flexible_vlan_tagging
   | it_link_mode
   | it_native_vlan_id
   | it_unit
;

intt_null
:
   (
      TRACEOPTIONS
   ) s_null_filler
;

isofamt_address
:
   ADDRESS ISO_ADDRESS
;

isofamt_mtu
:
   MTU DEC
;

it_apply_groups
:
   s_apply_groups
;

it_apply_groups_except
:
   s_apply_groups_except
;

it_common
:
   it_apply_groups
   | it_apply_groups_except
   | it_description
   | it_disable
   | it_enable
   | it_family
   | it_mtu
   | it_null
   | it_speed
   | it_vlan_id
   | it_vlan_id_list
   | it_vlan_tagging
;

it_description
:
   s_description
;

it_disable
:
   DISABLE
;

it_enable
:
   ENABLE
;

it_family
:
   FAMILY it_family_tail
;

it_family_tail
:
   famt_bridge
   | famt_ccc
   | famt_inet
   | famt_inet6
   | famt_iso
   | famt_mpls
;

it_flexible_vlan_tagging
:
   FLEXIBLE_VLAN_TAGGING
;

it_link_mode
:
   LINK_MODE FULL_DUPLEX
;

it_mtu
:
   MTU size = DEC
;

it_native_vlan_id
:
   NATIVE_VLAN_ID id = DEC
;

it_null
:
   (
      AGGREGATED_ETHER_OPTIONS
      | BANDWIDTH
      | ENCAPSULATION
      | FABRIC_OPTIONS
      | FRAMING
      | GIGETHER_OPTIONS
      | HOLD_TIME
      | INTERFACE_TRANSMIT_STATISTICS
      | MULTISERVICE_OPTIONS
      | NO_TRAPS
      | REDUNDANT_ETHER_OPTIONS
      | SONET_OPTIONS
      | TRACEOPTIONS
      | TRAPS
      | TUNNEL
   ) s_null_filler
;

it_peer_unit
:
   PEER_UNIT unit = DEC
;

it_speed
:
   SPEED DEC speed_abbreviation
;

it_unit
:
   UNIT
   (
      WILDCARD
      | num = DEC
   ) it_unit_tail
;

it_unit_tail
:
// intentional blank

   | it_common
   | it_peer_unit
;

it_vlan_id
:
   VLAN_ID id = DEC
;

it_vlan_id_list
:
   VLAN_ID_LIST subrange
;

it_vlan_tagging
:
   VLAN_TAGGING
;

ivrrpt_accept_data
:
   ACCEPT_DATA
;

ivrrpt_advertise_interval
:
   ADVERTISE_INTERVAL DEC
;

ivrrpt_authentication_type
:
   AUTHENTICATION_TYPE SIMPLE
;

ivrrpt_preempt
:
   PREEMPT
;

ivrrpt_priority
:
   PRIORITY DEC
;

ivrrpt_track
:
   TRACK ivrrpt_track_tail
;

ivrrpt_track_tail
:
   ivrrptt_interface
   | ivrrptt_route
;

ivrrpt_virtual_address
:
   VIRTUAL_ADDRESS IP_ADDRESS
;

ivrrptt_interface
:
   INTERFACE interface_id ivrrptt_interface_tail
;

ivrrptt_interface_tail
:
   ivrrptti_priority_cost
;

ivrrptt_route
:
   ROUTE IP_PREFIX ROUTING_INSTANCE variable PRIORITY_COST DEC
;

ivrrptti_priority_cost
:
   PRIORITY_COST cost = DEC
;

mfamt_filter
:
   filter
;

mfamt_maximum_labels
:
   MAXIMUM_LABELS num = DEC
;

mfamt_mtu
:
   MTU DEC
;

s_interfaces
:
   INTERFACES s_interfaces_tail
;

s_interfaces_tail
:
   intt_apply_groups
   | intt_named
   | intt_null
;

speed_abbreviation
:
   G
   | M
;
