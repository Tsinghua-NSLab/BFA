parser grammar FlatJuniper_common;

options {
   tokenVocab = FlatJuniperLexer;
}

administrator_as
:
   DEC L
;

administrator_dec
:
   DEC
;

administrator_dotted_as
:
   DEC PERIOD DEC
;

administrator_ip
:
   DEC PERIOD DEC PERIOD DEC PERIOD DEC
;

as_path_expr
:
   (
      items += as_unit
   )+
;

as_set
:
   OPEN_BRACKET
   (
      items += DEC
   )+ CLOSE_BRACKET
;

as_unit
:
   as_set
   | DEC
;

ec_administrator
:
   administrator_as
   | administrator_dec
   | administrator_dotted_as
   | administrator_ip
;

ec_literal
:
   DEC COLON DEC COLON DEC
;

ec_named
:
   ec_type COLON ec_administrator COLON assigned_number = DEC
;

ec_type
:
   ORIGIN
   | TARGET
;

extended_community
:
   ec_literal
   | ec_named
;

icmp_code
:
   DESTINATION_HOST_UNKNOWN
   | DESTINATION_NETWORK_UNKNOWN
   | FRAGMENTATION_NEEDED
   | HOST_UNREACHABLE
   | NETWORK_UNREACHABLE
   | PORT_UNREACHABLE
;

icmp_type
:
   DEC
   | DESTINATION_UNREACHABLE
   | ECHO_REPLY
   | ECHO_REQUEST
   | NEIGHBOR_ADVERTISEMENT
   | NEIGHBOR_SOLICIT
   | PACKET_TOO_BIG
   | PARAMETER_PROBLEM
   | SOURCE_QUENCH
   | TIME_EXCEEDED
   | UNREACHABLE
;

interface_id
:
   name = VARIABLE
   (
      PERIOD unit = DEC
   )?
;

ip_option
:
   SECURITY
;

ip_protocol
:
   AH
   | DEC
   | DSTOPTS
   | EGP
   | ESP
   | FRAGMENT
   | GRE
   | HOP_BY_HOP
   | ICMP
   | ICMP6
   | ICMPV6
   | IGMP
   | IPIP
   | IPV6
   | OSPF
   | PIM
   | RSVP
   | SCTP
   | TCP
   | UDP
   | VRRP
;

origin_type
:
   EGP
   | IGP
   | INCOMPLETE
;

s_apply_groups
:
   APPLY_GROUPS name = variable
;

s_apply_groups_except
:
   APPLY_GROUPS_EXCEPT name = variable
;

s_description
:
   DESCRIPTION description = M_Description_DESCRIPTION?
;

pe_conjunction
:
   OPEN_PAREN policy_expression
   (
      DOUBLE_AMPERSAND policy_expression
   )+ CLOSE_PAREN
;

pe_disjunction
:
   OPEN_PAREN policy_expression
   (
      DOUBLE_PIPE policy_expression
   )+ CLOSE_PAREN
;

pe_nested
:
   OPEN_PAREN policy_expression CLOSE_PAREN
;

policy_expression
:
   pe_conjunction
   | pe_disjunction
   | pe_nested
   | variable
;

port
:
   AFS
   | BGP
   | BIFF
   | BOOTPC
   | BOOTPS
   | CMD
   | CVSPSERVER
   | DEC
   | DHCP
   | DOMAIN
   | EKLOGIN
   | EKSHELL
   | EXEC
   | FINGER
   | FTP
   | FTP_DATA
   | HTTP
   | HTTPS
   | IDENT
   | IMAP
   | KERBEROS_SEC
   | KLOGIN
   | KPASSWD
   | KRB_PROP
   | KRBUPDATE
   | KSHELL
   | LDAP
   | LDP
   | LOGIN
   | MOBILEIP_AGENT
   | MOBILIP_MN
   | MSDP
   | NETBIOS_DGM
   | NETBIOS_NS
   | NETBIOS_SSN
   | NFSD
   | NNTP
   | NTALK
   | NTP
   | POP3
   | PPTP
   | PRINTER
   | RADACCT
   | RADIUS
   | RIP
   | RKINIT
   | SMTP
   | SNMP
   | SNMPTRAP
   | SNPP
   | SOCKS
   | SSH
   | SUNRPC
   | SYSLOG
   | TACACS
   | TACACS_DS
   | TALK
   | TELNET
   | TFTP
   | TIMED
   | WHO
   | XDMCP
;

range
:
   range_list += subrange
   (
      COMMA range_list += subrange
   )*
;

routing_protocol
:
   AGGREGATE
   | BGP
   | DIRECT
   | ISIS
   | LDP
   | LOCAL
   | OSPF
   | RSVP
   | STATIC
;

subrange
:
   low = DEC
   (
      DASH high = DEC
   )?
;

s_null_filler
:
   ~( APPLY_GROUPS | NEWLINE )* s_apply_groups?
;

sc_literal
:
   COMMUNITY_LITERAL
;

sc_named
:
   NO_ADVERTISE
   | NO_EXPORT
;

standard_community
:
   sc_literal
   | sc_named
;

variable
:
   text = ~( NEWLINE | OPEN_PAREN | OPEN_BRACKET | OPEN_BRACE | WILDCARD )
;
