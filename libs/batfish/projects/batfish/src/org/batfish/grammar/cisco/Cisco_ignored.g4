parser grammar Cisco_ignored;

import Cisco_common;

options {
   tokenVocab = CiscoLexer;
}

null_block_stanza
:
   NO?
   (
      AAA
      | ACCESS_GROUP
      | ARCHIVE
      | ATM
      | BASH
      | BFD
      | CEF
      | CHAT_SCRIPT
      | CLASS_MAP
      | CLI
      | CONFDCONFIG
      | CONFIGURATION
      | CONTROL_PLANE
      | CONTROLLER
      | COPY
      | CRYPTO
      | DIAL_PEER
      | DOMAIN
      | END
      | EVENT_HANDLER
      | FEX
      | FLOW
      | FPD
      | GATEKEEPER
      | GATEWAY
      | GROUP
      | GROUP_POLICY
      |
      (
         IP
         (
            (
               ACCESS_LIST
               (
               	  LOGGING
                  | LOG_UPDATE
               )
            )
            | ACCOUNTING_LIST
            | ACCOUNTING_THRESHOLD
            | DECAP_GROUP
            | DHCP
            | FLOW_TOP_TALKERS
            | INSPECT
            |
            (
               OSPF NAME_LOOKUP
            )
            | POLICY_LIST
            | SLA
            | SOURCE
            | VIRTUAL_ROUTER
            |
            (
               VRF ~NEWLINE
            )
         )
      )
      | IPC
      |
      (
         IPV4
         (
            ASSEMBLER
            | CONFLICT_POLICY
            | HARDWARE
            | UNNUMBERED
            | VIRTUAL
         )
      )
      |
      (
         IPV6
         (
            ACCESS_LIST
            | CONFLICT_POLICY
            | HARDWARE
         )
      )
      | KEY
      | KRON
      | L2 VFI
      | L2TP_CLASS
      | LINE
      | LOGGING
      | LOGIN
      | MAC
      | MAC_LEARN
      | MANAGEMENT
      | MAP_CLASS
      | MAP_LIST
      | MLAG
      | MODULE
      | MONITOR
      |
      (
         MPLS
         (
         	LABEL
            | 
            (
               LDP ~NEWLINE
            )
            | OAM
         )
      )
      | NO_BANNER
      | NSR
      | ONE
      | OPENFLOW
      | PLAT
      | POLICY_MAP
      | POWEROFF
      | PSEUDOWIRE_CLASS
      | PTP
      | REDUNDANCY
      | RMON
      | ROLE
      | SAMPLER
      | SCCP
      | SDR
      | SFLOW
      | SPANNING_TREE
      | STCAPP
      | SVCLC
      | TACACS
      | TACACS_SERVER
      | TCP
      | TEMPLATE
      | TERMINAL
      | TRACE
      | TRACK
      | TRANSCEIVER
      | USERGROUP
      | VDC
      |
      (
         VLAN DEC
      )
      | VOICE
      | VOICE_PORT
      | VPC
      | VPDN_GROUP
   ) ~NEWLINE* NEWLINE
   (
      description_line
      | null_block_substanza
      | null_block_substanza_full
   )*
;

null_block_substanza
:
   (
      NO?
      (
         ABSOLUTE_TIMEOUT
         | ACCEPT_DIALIN
         | ACCEPT_LIFETIME
         | ACCESS_CLASS
         | ACCOUNTING
         | ACCOUNTING_SERVER_GROUP
         | ACTION
         | ACTIVATION_CHARACTER
         | ADDRESS
         | ADDRESS_POOL
         | ADMINISTRATIVE_WEIGHT
         | AESA
         | ANYCONNECT
         | ARCHIVE_LENGTH
         | ARCHIVE_SIZE
         | ASSOCIATE
         | ASSOCIATION
         | AUTHENTICATION
         | AUTHENTICATION_SERVER_GROUP
         | AUTHORIZATION
         | AUTHORIZATION_REQUIRED
         | AUTHORIZATION_SERVER_GROUP
         | AUTO_SYNC
         | AUTOSELECT
         | BACKGROUND_ROUTES_ENABLE
         | BACKUPCRF
         | BANDWIDTH
         | BANNER
         | BIND
         | BRIDGE
         | CABLELENGTH
         | CACHE
         | CACHE_TIMEOUT
         | CALL
         | CALLER_ID
         | CAS_CUSTOM
         | CERTIFICATE
         | CHANNEL_GROUP
         | CHANNELIZED
         | CLASS
         | CLIENT_GROUP
         | CLIENT_IDENTIFIER
         | CLIENT_NAME
         | CLOCK
         | COLLECT
         | COMMAND
         | CONFORM_ACTION
         | CONGESTION_CONTROL
         | CONTEXT
         | CPTONE
         | CREDENTIALS
         | CRL
         | CRYPTOGRAPHIC_ALGORITHM
         | DATABITS
         | DBL
         | DEFAULT_ACTION
         | DEFAULT_DOMAIN
         | DEFAULT_GROUP_POLICY
         | DEFAULT_ROUTER
         | DELAY
         | DENY
         | DESCRIPTION
         | DESTINATION
         | DEVICE
         | DIAGNOSTIC
         | DNS_SERVER
         | DOMAIN_ID
         | DROP
         | DS0_GROUP
         | DOMAIN_NAME
         | ECHO
         | ENCAPSULATION
         | ENCRYPTION
         | END_CLASS_MAP
         | END_POLICY_MAP
         | ENROLLMENT
         | ERSPAN_ID
         | ESCAPE_CHARACTER
         | EXCEED_ACTION
         | EXEC
         | EXEC_TIMEOUT
         | EXIT
         | EXPECT
         | EXPORT
         | EXPORT_PROTOCOL
         | EXPORTER
         | FABRIC
         | FAILED
         | FAILOVER
         | FAIR_QUEUE
         | FALLBACK_DN
         | FDL
         | FILE_BROWSING
         | FILE_ENTRY
         | FILE_SIZE
         | FLUSH_AT_ACTIVATION
         | FQDN
         | FRAMING
         | FREQUENCY
         | FT
         | GATEWAY
         | GID
         | GROUP
         | GROUP_ALIAS
         | GROUP_POLICY
         | GROUP_URL
         | HEARTBEAT_TIME
         | HIDDEN
         | HIDDEN_SHARES
         | HIDEKEYS
         | HIGH_AVAILABILITY
         | HISTORY
         | HOMEDIR
         | HOST
         | ICMP_ECHO
         | IDLE
         | IDLE_TIMEOUT
         | IMPORT
         | INSERVICE
         | INSPECT
         | INSTANCE
         | INTEGRITY
         |
         (
            INTERFACE POLICY
         )
         | INTERVAL
         |
         (
            (
               IP
               | IPV6
            )
            (
               ACCESS_CLASS
               | ACCESS_GROUP
               | ADDRESS
               | FLOW
            )
         )
         | IPSEC_UDP
         | IPX
         | IPV6_ADDRESS_POOL
         | ISAKMP
         | KEEPALIVE_ENABLE
         | KEY_STRING
         | KEYPAIR
         | KEYPATH
         | KEYRING
         | L2TP
         | LEASE
         | LENGTH
         | LIMIT_RESOURCE
         | LINE
         | LINECODE
         | LLDP
         | LOCAL_INTERFACE
         | LOCAL_IP
         | LOCAL_PORT
         | LOCATION
         | LOG
         | LOGGING
         | LOGIN
         | LPTS
         | MAIN_CPU
         | MATCH
         | MAXIMUM
         | MESSAGE_LENGTH
         | MODE
         | MODEM
         | MONITORING
         | MTU
         | NAME
         | NAMESPACE
         | NAT
         | NATPOOL
         | NEGOTIATE
         | NETWORK
         | NODE
         | NOTIFY
         | OBJECT
         | OPEN
         | OPS
         | PARAMETERS
         | PARENT
         | PARITY
         | PASSWORD
         | PASSWORD_STORAGE
         | PATH_JITTER
         | PAUSE
         | PEER
         | PEER_ADDRESS
         | PEER_CONFIG_CHECK_BYPASS
         | PEER_GATEWAY
         | PEER_KEEPALIVE
         | PEER_LINK
         | PERMIT
         | PERSISTENT
         | PICKUP
         | PINNING
         | POLICE
         | POLICY
         | POLICY_LIST
         | POLICY_MAP
         | PORT
         | PREDICTOR
         | PRE_SHARED_KEY
         | PREEMPT
         | PREFIX
         | PRI_GROUP
         | PRIORITY
         | PRIVILEGE
         | PROBE
         | PROPOSAL
         | PROTOCOL
         | QUEUE_BUFFERS
         | QUEUE_LIMIT
         | RANDOM_DETECT
         | RD
         | REAL
         | RECEIVE
         | RECORD
         | RECORD_ENTRY
         | REDISTRIBUTE
         | RELOAD
         | RELOAD_DELAY
         | REMARK
         | REMOTE_IP
         | REMOTE_PORT
         | REMOTE_SPAN
         | REMOVED
         | REQUEST
         | REQUEST_DATA_SIZE
         | RESOURCES
         | RETRANSMIT
         | RETRIES
         | REVERSE_ROUTE
         | REVISION
         | RING
         | ROLE
         | ROTARY
         | ROUTE
         | ROUTE_TARGET
         | RULE
         | SCHEME
         | SECRET
         | SEND_LIFETIME
         | SEQUENCE
         | SERVER
         | SERVERFARM
         | SERVER_PRIVATE
         | SERVICE
         | SERVICE_POLICY
         | SERVICE_TYPE
         | SESSION_DISCONNECT_WARNING
         | SESSION_LIMIT
         | SESSION_TIMEOUT
         | SET
         | SEVERITY
         | SHAPE
         | SHUT
         | SHUTDOWN
         | SINGLE_CONNECTION
         | SINGLE_ROUTER_MODE
         | SORT_BY
         | SOURCE
         | SPANNING_TREE
         | SPEED
         | SPLIT_TUNNEL_NETWORK_LIST
         | SPLIT_TUNNEL_POLICY
         | SSH_KEYDIR
         | STICKY
         | STOPBITS
         | STP
         | SUBJECT_NAME
         | SWITCHBACK
         | SWITCHPORT
         | SYNC
         | SYSTEM_PRIORITY
         | TAG
         | TASKGROUP
         | TB_VLAN1
         | TB_VLAN2
         | TCP_CONNECT
         | TERMINAL_TYPE
         | THRESHOLD
         | TIMEOUT
         | TIMEOUTS
         | TIMER
         | TIMESTAMP
         | TIMING
         | TOP
         | TOS
         | TRANSPORT
         | TRIGGER
         | TRUNK
         | TRUST
         | TUNNEL
         | TUNNEL_GROUP
         | UDP_JITTER
         | UID
         | UPDATE_CALENDAR
         | USE_VRF
         | USERS
         | VIOLATE_ACTION
         | VIRTUAL
         | VIRTUAL_TEMPLATE
         | VM_CPU
         | VM_MEMORY
         | VPN_FILTER
         | VPN_IDLE_TIMEOUT
         | VPN_TUNNEL_PROTOCOL
         | VSERVER
         | VTY_POOL
         | WEBVPN
         | WINS_SERVER
         | WITHOUT_CSD
         | XML_CONFIG
      )
      (
         remaining_tokens += ~NEWLINE
      )* NEWLINE
   )
;

null_block_substanza_full
:
   (
      (
         VLAN DEC
         (
            CLIENT
            | SERVER
         )
      )
      |
      (
         VRF variable
      )
   ) NEWLINE
;

null_standalone_stanza_DEPRECATED_DO_NOT_ADD_ITEMS
:
   (
      NO
   )?
   (
      AAA_SERVER
      | ABSOLUTE_TIMEOUT
      |
      (
         ACCESS_LIST
         (
            (
               DEC REMARK
            )
            | VARIABLE
         )
      )
      | ACCOUNTING_PORT
      | ACTION
      | ACTIVE
      | ALERT_GROUP
      | ALIAS
      | ANYCONNECT
      | ANYCONNECT_ESSENTIALS
      | AP
      | AQM_REGISTER_FNF
      | ARP
      | ASA
      | ASDM
      | ASSOCIATE
      | ASYNC_BOOTP
      | AUTHENTICATION
      | AUTHENTICATION_PORT
      | AUTO
      | BOOT
      | BOOT_END_MARKER
      | BOOT_START_MARKER
      | BRIDGE
      | CALL
      | CALL_HOME
      | CARD
      | CCM_MANAGER
      | CDP
      | CFS
      | CIPC
      | CLOCK
      | CLUSTER
      | CNS
      | CODEC
      | CONFIG_REGISTER
      | CONSOLE
      | CONTACT_EMAIL_ADDR
      | CRL
      | CTL_FILE
      | CTS
      | DEC
      | DEFAULT
      | DESCRIPTION
      | DESTINATION
      | DEVICE_SENSOR
      | DHCPD
      | DIAGNOSTIC
      | DIALER_LIST
      | DISABLE
      | DNS
      | DNS_GUARD
      | DOMAIN_NAME
      | DOT11
      | DSP
      | DSPFARM
      | DSS
      | DYNAMIC_ACCESS_POLICY_RECORD
      | ENABLE
      | ENCR
      | ENROLLMENT
      | ENVIRONMENT
      | ERRDISABLE
      | ESCAPE_CHARACTER
      | EVENT
      | EXCEPTION
      | EXEC
      | FABRIC
      | FACILITY_ALARM
      | FAILOVER
      | FEATURE
      | FILE
      | FIREWALL
      | FIRMWARE
      | FLOWCONTROL
      | FRAME_RELAY
      | FQDN
      | FTP
      | FTP_SERVER
      | GROUP_OBJECT
      | HARDWARE
      | HASH
      | HISTORY
      | HTTP
      | HW_MODULE
      | ICMP
      | ICMP_OBJECT
      | IDENTITY
      | INACTIVITY_TIMER
      |
      (
         IP
         (
            ADDRESS_POOL
            | ADMISSION
            | ALIAS
            | ARP
            | AUDIT
            | AUTH_PROXY
            | BOOTP
            | BGP_COMMUNITY
            | CEF
            | CLASSLESS
            | DEFAULT_NETWORK
            | DEVICE
            | DOMAIN
            | DOMAIN_LIST
            | DOMAIN_LOOKUP
            | DOMAIN_NAME
            | DVMRP
            | EXTCOMMUNITY_LIST
            | FINGER
            | FLOW_CACHE
            | FLOW_EXPORT
            | FORWARD_PROTOCOL
            | FTP
            | GRATUITOUS_ARPS
            | HOST
            | HOST_ROUTING
            | HTTP
            | ICMP
            | IGMP
            | LOAD_SHARING
            | LOCAL
            | MFIB
            | MROUTE
            | MSDP
            | MULTICAST
            | MULTICAST_ROUTING
            | NAME_SERVER
            | NAT
            | PIM
            | RADIUS
            | RCMD
            | ROUTING //might want to use this eventually

            | SAP
            | SCP
            | SLA
            | SOURCE_ROUTE
            | SSH
            | SUBNET_ZERO
            | TACACS
            | TCP
            | TELNET
            | TFTP
            | VERIFY
         )
      )
      | IP_ADDRESS_LITERAL
      |
      (
         IPV6
         (
            CEF
            | HOST
            | LOCAL
            | MFIB
            | MFIB_MODE
            | MLD
            | MULTICAST
            | MULTICAST_ROUTING
            | ND
            |
            (
               OSPF NAME_LOOKUP
            )
            | PIM
            | ROUTE
            | SOURCE_ROUTE
            | UNICAST_ROUTING
         )
      )
      | ISDN
      | KEEPOUT
      | KEYPAIR
      | KEYRING
      | LDAP_BASE_DN
      | LDAP_LOGIN
      | LDAP_LOGIN_DN
      | LDAP_NAMING_ATTRIBUTE
      | LDAP_SCOPE
      | LICENSE
      | LIFETIME
      | LLDP
      | LOCATION
      | MAC_ADDRESS_TABLE
      | MAIL_SERVER
      | MAXIMUM
      | MEDIA_TERMINATION
      | MEMORY_SIZE
      | MGCP
      | MICROCODE
      | MLS
      | MODE
      | MODEM
      | MTA
      | MTU
      | MULTILINK
      | MVR
      | NAME_SERVER
      | NAME
      | NAMES
      | NAT
      | NAT_CONTROL
      | NETCONF
      | NETWORK_OBJECT
      | NETWORK_CLOCK_PARTICIPATE
      | NETWORK_CLOCK_SELECT
      | NTP
      | OBJECT_GROUP
      | OWNER
      | PAGER
      | PARSER
      | PARTICIPATE
      | PASSWORD
      | PERCENT
      | PHONE_PROXY
      | PLATFORM
      | PORT_CHANNEL
      | PORT_OBJECT
      | POWER
      | PRIORITY
      | PRIORITY_QUEUE
      | PRIVILEGE
      | PROCESS
      | PROFILE
      | PROMPT
      | PROTOCOL_OBJECT
      | QOS
      | QUIT
      | RADIUS_COMMON_PW
      | RADIUS_SERVER
      | RD
      | RECORD_ENTRY
      | REDIRECT_FQDN
      | RESOURCE
      | RESOURCE_POOL
      | REVERSE_ROUTE
      | REVOCATION_CHECK
      | ROUTE
      | ROUTE_TARGET
      | RSAKEYPAIR
      | RTR
      | SAME_SECURITY_TRAFFIC
      | SCHEDULE
      | SCHEDULER
      | SCRIPTING
      | SDM
      | SECURITY
      | SENDER
      | SERIAL_NUMBER
      | SERVER
      | SERVER_TYPE
      | SERVICE
      | SERVICE_POLICY
      | SETUP
      | SHELL
      | SMTP_SERVER
      | SNMP
      | SNMP_SERVER
      | SOURCE
      | SOURCE_INTERFACE
      | SOURCE_IP_ADDRESS
      | SPANNING_TREE
      | SPD
      | SPE
      | SPEED
      | STOPBITS
      | SSH
      | SSL
      | STATIC
      | SUBJECT_NAME
      | SUBNET
      | SUBSCRIBER
      | SUBSCRIBE_TO
      | SUBSCRIBE_TO_ALERT_GROUP
      | SWITCH
      | SYSOPT
      | TABLE_MAP
      | TAG_SWITCHING
      | TELNET
      | TFTP_SERVER
      | THREAT_DETECTION
      | TLS_PROXY
      | TRANSLATE
      | TRANSPORT
      | TUNNEL_GROUP_LIST
      | TYPE
      | UDLD
      | UNABLE
      | UPGRADE
      | USER_IDENTITY
      | USE_VRF
      | USERNAME
      | VALIDATION_USAGE
      | VERSION
      |
      (
         VLAN
         (
            ACCESS_LOG
            | CONFIGURATION
            | DOT1Q
            | INTERNAL
         )
      )
      | VMPS
      | VPDN
      | VPN
      | VTP
      | VOICE_CARD
      | WEBVPN
      | WLAN
      | WSMA
      | X25
      | X29
      | XLATE
      | XML SERVER
   )
   (
      remaining_tokens += ~NEWLINE
   )* NEWLINE
;
