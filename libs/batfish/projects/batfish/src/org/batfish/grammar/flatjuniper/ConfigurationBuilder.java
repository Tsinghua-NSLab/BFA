package org.batfish.grammar.flatjuniper;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.batfish.grammar.flatjuniper.FlatJuniperCombinedParser;
import org.batfish.grammar.flatjuniper.FlatJuniperParser.*;
import org.batfish.common.BatfishException;
import org.batfish.main.Warnings;
import org.batfish.representation.AsPath;
import org.batfish.representation.AsSet;
import org.batfish.representation.ExtendedCommunity;
import org.batfish.representation.Ip;
import org.batfish.representation.IpProtocol;
import org.batfish.representation.IsoAddress;
import org.batfish.representation.NamedPort;
import org.batfish.representation.Prefix;
import org.batfish.representation.RoutingProtocol;
import org.batfish.representation.juniper.AggregateRoute;
import org.batfish.representation.juniper.BgpGroup;
import org.batfish.representation.juniper.CommunityList;
import org.batfish.representation.juniper.CommunityListLine;
import org.batfish.representation.juniper.Family;
import org.batfish.representation.juniper.FirewallFilter;
import org.batfish.representation.juniper.FwFrom;
import org.batfish.representation.juniper.FwFromDestinationAddress;
import org.batfish.representation.juniper.FwFromDestinationPort;
import org.batfish.representation.juniper.FwFromProtocol;
import org.batfish.representation.juniper.FwFromSourceAddress;
import org.batfish.representation.juniper.FwFromSourcePort;
import org.batfish.representation.juniper.FwTerm;
import org.batfish.representation.juniper.FwThenAccept;
import org.batfish.representation.juniper.FwThenDiscard;
import org.batfish.representation.juniper.FwThenNextTerm;
import org.batfish.representation.juniper.FwThenNop;
import org.batfish.representation.juniper.GeneratedRoute;
import org.batfish.representation.juniper.Interface;
import org.batfish.representation.juniper.IpBgpGroup;
import org.batfish.representation.juniper.IsisInterfaceLevelSettings;
import org.batfish.representation.juniper.IsisLevelSettings;
import org.batfish.representation.juniper.IsisSettings;
import org.batfish.representation.juniper.JuniperVendorConfiguration;
import org.batfish.representation.juniper.NamedBgpGroup;
import org.batfish.representation.juniper.OspfArea;
import org.batfish.representation.juniper.PolicyStatement;
import org.batfish.representation.juniper.PrefixList;
import org.batfish.representation.juniper.PsFrom;
import org.batfish.representation.juniper.PsFromAsPath;
import org.batfish.representation.juniper.PsFromColor;
import org.batfish.representation.juniper.PsFromCommunity;
import org.batfish.representation.juniper.PsFromInterface;
import org.batfish.representation.juniper.PsFromPrefixList;
import org.batfish.representation.juniper.PsFromProtocol;
import org.batfish.representation.juniper.PsFromRouteFilter;
import org.batfish.representation.juniper.PsTerm;
import org.batfish.representation.juniper.PsThen;
import org.batfish.representation.juniper.PsThenAccept;
import org.batfish.representation.juniper.PsThenCommunityAdd;
import org.batfish.representation.juniper.PsThenCommunityDelete;
import org.batfish.representation.juniper.PsThenCommunitySet;
import org.batfish.representation.juniper.PsThenLocalPreference;
import org.batfish.representation.juniper.PsThenMetric;
import org.batfish.representation.juniper.PsThenNextHopIp;
import org.batfish.representation.juniper.PsThenReject;
import org.batfish.representation.juniper.RouteFilter;
import org.batfish.representation.juniper.RouteFilterLine;
import org.batfish.representation.juniper.RouteFilterLineExact;
import org.batfish.representation.juniper.RouteFilterLineLengthRange;
import org.batfish.representation.juniper.RouteFilterLineLonger;
import org.batfish.representation.juniper.RouteFilterLineOrLonger;
import org.batfish.representation.juniper.RouteFilterLineThrough;
import org.batfish.representation.juniper.RouteFilterLineUpTo;
import org.batfish.representation.juniper.RoutingInformationBase;
import org.batfish.representation.juniper.RoutingInstance;
import org.batfish.representation.juniper.StaticRoute;
import org.batfish.representation.juniper.BgpGroup.BgpGroupType;
import org.batfish.util.SubRange;
import org.batfish.util.Util;

public class ConfigurationBuilder extends FlatJuniperParserBaseListener {

   private static final AggregateRoute DUMMY_AGGREGATE_ROUTE = new AggregateRoute(
         Prefix.ZERO);

   private static final BgpGroup DUMMY_BGP_GROUP = new BgpGroup();

   private static final StaticRoute DUMMY_STATIC_ROUTE = new StaticRoute(
         Prefix.ZERO);

   private static final String F_BGP_LOCAL_AS_LOOPS = "protocols - bgp - group? - local-as - loops";

   private static final String F_BGP_LOCAL_AS_PRIVATE = "protocols - bgp - group? - local-as - private";

   private static final String F_COMPLEX_POLICY = "boolean combination of policy-statements";

   private static final String F_FIREWALL_TERM_THEN_ROUTING_INSTANCE = "firewall - filter - term - then - routing-instance";

   private static final String F_IPV6 = "ipv6 - other";

   private static final String F_POLICY_TERM_THEN_NEXT_HOP = "policy-statement - term - then - next-hop";

   public static NamedPort getNamedPort(PortContext ctx) {
      if (ctx.AFS() != null) {
         return NamedPort.AFS;
      }
      else if (ctx.BGP() != null) {
         return NamedPort.BGP;
      }
      else if (ctx.BIFF() != null) {
         return NamedPort.BIFFudp_OR_EXECtcp;
      }
      else if (ctx.BOOTPC() != null) {
         return NamedPort.BOOTPC;
      }
      else if (ctx.BOOTPS() != null) {
         return NamedPort.BOOTPS_OR_DHCP;
      }
      else if (ctx.CMD() != null) {
         return NamedPort.CMDtcp_OR_SYSLOGudp;
      }
      else if (ctx.CVSPSERVER() != null) {
         return NamedPort.CVSPSERVER;
      }
      else if (ctx.DHCP() != null) {
         return NamedPort.BOOTPS_OR_DHCP;
      }
      else if (ctx.DOMAIN() != null) {
         return NamedPort.DOMAIN;
      }
      else if (ctx.EKLOGIN() != null) {
         return NamedPort.EKLOGIN;
      }
      else if (ctx.EKSHELL() != null) {
         return NamedPort.EKSHELL;
      }
      else if (ctx.EXEC() != null) {
         return NamedPort.BIFFudp_OR_EXECtcp;
      }
      else if (ctx.FINGER() != null) {
         return NamedPort.FINGER;
      }
      else if (ctx.FTP() != null) {
         return NamedPort.FTP;
      }
      else if (ctx.FTP_DATA() != null) {
         return NamedPort.FTP_DATA;
      }
      else if (ctx.HTTP() != null) {
         return NamedPort.HTTP;
      }
      else if (ctx.HTTPS() != null) {
         return NamedPort.HTTPS;
      }
      else if (ctx.IDENT() != null) {
         return NamedPort.IDENT;
      }
      else if (ctx.IMAP() != null) {
         return NamedPort.IMAP;
      }
      else if (ctx.KERBEROS_SEC() != null) {
         return NamedPort.KERBEROS_SEC;
      }
      else if (ctx.KLOGIN() != null) {
         return NamedPort.KLOGIN;
      }
      else if (ctx.KPASSWD() != null) {
         return NamedPort.KPASSWD;
      }
      else if (ctx.KRB_PROP() != null) {
         return NamedPort.KRB_PROP;
      }
      else if (ctx.KRBUPDATE() != null) {
         return NamedPort.KRBUPDATE;
      }
      else if (ctx.KSHELL() != null) {
         return NamedPort.KSHELL;
      }
      else if (ctx.LDAP() != null) {
         return NamedPort.LDAP;
      }
      else if (ctx.LDP() != null) {
         return NamedPort.LDP;
      }
      else if (ctx.LOGIN() != null) {
         return NamedPort.LOGINtcp_OR_WHOudp;
      }
      else if (ctx.MOBILEIP_AGENT() != null) {
         return NamedPort.MOBILE_IP_AGENT;
      }
      else if (ctx.MOBILIP_MN() != null) {
         return NamedPort.MOBILE_IP_MN;
      }
      else if (ctx.MSDP() != null) {
         return NamedPort.MSDP;
      }
      else if (ctx.NETBIOS_DGM() != null) {
         return NamedPort.NETBIOS_DGM;
      }
      else if (ctx.NETBIOS_NS() != null) {
         return NamedPort.NETBIOS_NS;
      }
      else if (ctx.NETBIOS_SSN() != null) {
         return NamedPort.NETBIOS_SSN;
      }
      else if (ctx.NFSD() != null) {
         return NamedPort.NFSD;
      }
      else if (ctx.NNTP() != null) {
         return NamedPort.NNTP;
      }
      else if (ctx.NTALK() != null) {
         return NamedPort.NTALK;
      }
      else if (ctx.NTP() != null) {
         return NamedPort.NTP;
      }
      else if (ctx.POP3() != null) {
         return NamedPort.POP3;
      }
      else if (ctx.PPTP() != null) {
         return NamedPort.PPTP;
      }
      else if (ctx.PRINTER() != null) {
         return NamedPort.LDP;
      }
      else if (ctx.RADACCT() != null) {
         return NamedPort.RADIUS_JUNIPER;
      }
      else if (ctx.RADIUS() != null) {
         return NamedPort.RADIUS_JUNIPER;
      }
      else if (ctx.RIP() != null) {
         return NamedPort.RIP;
      }
      else if (ctx.RKINIT() != null) {
         return NamedPort.RKINIT;
      }
      else if (ctx.SMTP() != null) {
         return NamedPort.SMTP;
      }
      else if (ctx.SNMP() != null) {
         return NamedPort.SNMP;
      }
      else if (ctx.SNMPTRAP() != null) {
         return NamedPort.SNMPTRAP;
      }
      else if (ctx.SNPP() != null) {
         return NamedPort.SNPP;
      }
      else if (ctx.SOCKS() != null) {
         return NamedPort.SOCKS;
      }
      else if (ctx.SSH() != null) {
         return NamedPort.SSH;
      }
      else if (ctx.SUNRPC() != null) {
         return NamedPort.SUNRPC;
      }
      else if (ctx.SYSLOG() != null) {
         return NamedPort.CMDtcp_OR_SYSLOGudp;
      }
      else if (ctx.TACACS() != null) {
         return NamedPort.TACACS;
      }
      else if (ctx.TACACS_DS() != null) {
         return NamedPort.TACACS_DS;
      }
      else if (ctx.TALK() != null) {
         return NamedPort.TALK;
      }
      else if (ctx.TELNET() != null) {
         return NamedPort.TELNET;
      }
      else if (ctx.TFTP() != null) {
         return NamedPort.TFTP;
      }
      else if (ctx.TIMED() != null) {
         return NamedPort.TIMED;
      }
      else if (ctx.WHO() != null) {
         return NamedPort.LOGINtcp_OR_WHOudp;
      }
      else if (ctx.XDMCP() != null) {
         return NamedPort.XDMCP;
      }
      else {
         throw new BatfishException("missing port-number mapping for port: \""
               + ctx.getText() + "\"");
      }
   }

   public static int getPortNumber(PortContext ctx) {
      if (ctx.DEC() != null) {
         int port = toInt(ctx.DEC());
         return port;
      }
      else {
         NamedPort namedPort = getNamedPort(ctx);
         return namedPort.number();
      }
   }

   private static long toCommunityLong(Sc_literalContext sc_literal) {
      String text = sc_literal.COMMUNITY_LITERAL().getText();
      return Util.communityStringToLong(text);
   }

   private static long toCommunityLong(Sc_namedContext ctx) {
      if (ctx.NO_ADVERTISE() != null) {
         return 0xFFFFFF02l;
      }
      if (ctx.NO_EXPORT() != null) {
         return 0xFFFFFF01l;
      }
      else {
         throw new BatfishException(
               "missing named-community-to-long mapping for: \""
                     + ctx.getText() + "\"");
      }
   }

   private static long toCommunityLong(Standard_communityContext ctx) {
      if (ctx.sc_literal() != null) {
         return toCommunityLong(ctx.sc_literal());
      }
      else if (ctx.sc_named() != null) {
         return toCommunityLong(ctx.sc_named());
      }
      else {
         throw new BatfishException("Cannot convert to community long");
      }
   }

   private static Integer toInt(TerminalNode node) {
      return toInt(node.getSymbol());
   }

   private static int toInt(Token token) {
      return Integer.parseInt(token.getText());
   }

   private static IpProtocol toIpProtocol(Ip_protocolContext ctx) {
      if (ctx.DEC() != null) {
         int protocolNum = toInt(ctx.DEC());
         return IpProtocol.fromNumber(protocolNum);
      }
      else if (ctx.AH() != null) {
         return IpProtocol.AHP;
      }
      else if (ctx.DSTOPTS() != null) {
         return IpProtocol.IPv6_Opts;
      }
      else if (ctx.EGP() != null) {
         return IpProtocol.EGP;
      }
      else if (ctx.ESP() != null) {
         return IpProtocol.ESP;
      }
      else if (ctx.FRAGMENT() != null) {
         return IpProtocol.IPv6_Frag;
      }
      else if (ctx.GRE() != null) {
         return IpProtocol.GRE;
      }
      else if (ctx.HOP_BY_HOP() != null) {
         return IpProtocol.HOPOPT;
      }
      else if (ctx.ICMP() != null) {
         return IpProtocol.ICMP;
      }
      else if (ctx.ICMP6() != null) {
         return IpProtocol.IPv6_ICMP;
      }
      else if (ctx.ICMPV6() != null) {
         return IpProtocol.IPv6_ICMP;
      }
      else if (ctx.IGMP() != null) {
         return IpProtocol.IGMP;
      }
      else if (ctx.IPIP() != null) {
         return IpProtocol.IPINIP;
      }
      else if (ctx.IPV6() != null) {
         return IpProtocol.IPv6;
      }
      else if (ctx.OSPF() != null) {
         return IpProtocol.OSPF;
      }
      else if (ctx.PIM() != null) {
         return IpProtocol.PIM;
      }
      else if (ctx.RSVP() != null) {
         return IpProtocol.RSVP;
      }
      else if (ctx.SCTP() != null) {
         return IpProtocol.SCTP;
      }
      else if (ctx.TCP() != null) {
         return IpProtocol.TCP;
      }
      else if (ctx.UDP() != null) {
         return IpProtocol.UDP;
      }
      else if (ctx.VRRP() != null) {
         return IpProtocol.VRRP;
      }
      else {
         throw new BatfishException(
               "missing protocol-enum mapping for protocol: \"" + ctx.getText()
                     + "\"");
      }
   }

   private static RoutingProtocol toRoutingProtocol(Routing_protocolContext ctx) {
      if (ctx.AGGREGATE() != null) {
         return RoutingProtocol.AGGREGATE;
      }
      else if (ctx.BGP() != null) {
         return RoutingProtocol.BGP;
      }
      else if (ctx.DIRECT() != null) {
         return RoutingProtocol.CONNECTED;
      }
      else if (ctx.ISIS() != null) {
         return RoutingProtocol.ISIS;
      }
      else if (ctx.LDP() != null) {
         return RoutingProtocol.LDP;
      }
      else if (ctx.LOCAL() != null) {
         return RoutingProtocol.LOCAL;
      }
      else if (ctx.OSPF() != null) {
         return RoutingProtocol.OSPF;
      }
      else if (ctx.RSVP() != null) {
         return RoutingProtocol.RSVP;
      }
      else if (ctx.STATIC() != null) {
         return RoutingProtocol.STATIC;
      }
      else {
         throw new BatfishException("missing routing protocol-enum mapping");
      }
   }

   private JuniperVendorConfiguration _configuration;

   private AggregateRoute _currentAggregateRoute;

   private OspfArea _currentArea;

   private BgpGroup _currentBgpGroup;

   private CommunityList _currentCommunityList;

   private FirewallFilter _currentFilter;

   private Family _currentFirewallFamily;

   private FwTerm _currentFwTerm;

   private GeneratedRoute _currentGeneratedRoute;

   private Interface _currentInterface;

   private Prefix _currentInterfacePrefix;

   private Interface _currentIsisInterface;

   private IsisInterfaceLevelSettings _currentIsisInterfaceLevelSettings;

   private IsisLevelSettings _currentIsisLevelSettings;

   private Interface _currentMasterInterface;

   private Interface _currentOspfInterface;

   private PolicyStatement _currentPolicyStatement;

   private PrefixList _currentPrefixList;

   private PsTerm _currentPsTerm;

   private Set<PsThen> _currentPsThens;

   private RoutingInformationBase _currentRib;

   private RouteFilter _currentRouteFilter;

   private RouteFilterLine _currentRouteFilterLine;

   private Prefix _currentRouteFilterPrefix;

   private RoutingInstance _currentRoutingInstance;

   private StaticRoute _currentStaticRoute;

   private FlatJuniperCombinedParser _parser;

   private final Map<PsTerm, RouteFilter> _termRouteFilters;

   private final String _text;

   private final Set<String> _unimplementedFeatures;

   private final Warnings _w;

   public ConfigurationBuilder(FlatJuniperCombinedParser parser, String text,
         Warnings warnings, Set<String> unimplementedFeatures) {
      _parser = parser;
      _text = text;
      _configuration = new JuniperVendorConfiguration(unimplementedFeatures);
      _currentRoutingInstance = _configuration.getDefaultRoutingInstance();
      _termRouteFilters = new HashMap<PsTerm, RouteFilter>();
      _unimplementedFeatures = unimplementedFeatures;
      _w = warnings;
   }

   @Override
   public void enterAt_interface(At_interfaceContext ctx) {
      Map<String, Interface> interfaces = _currentRoutingInstance
            .getInterfaces();
      String unitFullName = null;
      if (ctx.ip != null) {
         Ip ip = new Ip(ctx.ip.getText());
         for (Interface iface : interfaces.values()) {
            for (Interface unit : iface.getUnits().values()) {
               if (unit.getAllPrefixIps().contains(ip)) {
                  _currentOspfInterface = unit;
                  unitFullName = unit.getName();
               }
            }
         }
         if (_currentOspfInterface == null) {
            throw new BatfishException(
                  "Could not find interface with ip address: " + ip.toString());
         }
      }
      else {
         String name = ctx.id.name.getText();
         String unit = null;
         if (ctx.id.unit != null) {
            unit = ctx.id.unit.getText();
         }
         unitFullName = name + "." + unit;
         _currentOspfInterface = interfaces.get(name);
         if (_currentOspfInterface == null) {
            _currentOspfInterface = new Interface(name);
            interfaces.put(name, _currentOspfInterface);
         }
         if (unit != null) {
            Map<String, Interface> units = _currentOspfInterface.getUnits();
            _currentOspfInterface = units.get(unitFullName);
            if (_currentOspfInterface == null) {
               _currentOspfInterface = new Interface(unitFullName);
               units.put(unitFullName, _currentOspfInterface);
            }
         }
      }
      Ip currentArea = _currentArea.getAreaIp();
      if (ctx.at_interface_tail() != null
            && ctx.at_interface_tail().ait_passive() != null) {
         _currentOspfInterface.getOspfPassiveAreas().add(currentArea);
      }
      else {
         Ip interfaceActiveArea = _currentOspfInterface.getOspfActiveArea();
         if (interfaceActiveArea != null
               && !currentArea.equals(interfaceActiveArea)) {
            throw new BatfishException("Interface: \""
                  + unitFullName.toString()
                  + "\" assigned to multiple active areas");
         }
         _currentOspfInterface.setOspfActiveArea(currentArea);
      }
   }

   @Override
   public void enterBt_group(Bt_groupContext ctx) {
      String name = ctx.name.getText();
      Map<String, NamedBgpGroup> namedBgpGroups = _currentRoutingInstance
            .getNamedBgpGroups();
      NamedBgpGroup namedBgpGroup = namedBgpGroups.get(name);
      if (namedBgpGroup == null) {
         namedBgpGroup = new NamedBgpGroup(name);
         namedBgpGroup.setParent(_currentBgpGroup);
         namedBgpGroups.put(name, namedBgpGroup);
      }
      _currentBgpGroup = namedBgpGroup;
   }

   @Override
   public void enterBt_neighbor(Bt_neighborContext ctx) {
      if (ctx.IP_ADDRESS() != null) {
         Ip remoteAddress = new Ip(ctx.IP_ADDRESS().getText());
         Map<Ip, IpBgpGroup> ipBgpGroups = _currentRoutingInstance
               .getIpBgpGroups();
         IpBgpGroup ipBgpGroup = ipBgpGroups.get(remoteAddress);
         if (ipBgpGroup == null) {
            ipBgpGroup = new IpBgpGroup(remoteAddress);
            ipBgpGroup.setParent(_currentBgpGroup);
            ipBgpGroups.put(remoteAddress, ipBgpGroup);
         }
         _currentBgpGroup = ipBgpGroup;
      }
      else if (ctx.IPV6_ADDRESS() != null) {
         _currentBgpGroup = DUMMY_BGP_GROUP;
      }
   }

   @Override
   public void enterFromt_route_filter(Fromt_route_filterContext ctx) {
      if (ctx.IP_PREFIX() != null) {
         _currentRouteFilterPrefix = new Prefix(ctx.IP_PREFIX().getText());
         _currentRouteFilter = _termRouteFilters.get(_currentPsTerm);
         if (_currentRouteFilter == null) {
            String rfName = _currentPolicyStatement.getName() + ":"
                  + _currentPsTerm.getName();
            _currentRouteFilter = new RouteFilter(rfName);
            _termRouteFilters.put(_currentPsTerm, _currentRouteFilter);
            _configuration.getRouteFilters().put(rfName, _currentRouteFilter);
            PsFromRouteFilter from = new PsFromRouteFilter(rfName);
            _currentPsTerm.getFroms().add(from);
         }
      }
   }

   @Override
   public void enterFromt_route_filter_then(Fromt_route_filter_thenContext ctx) {
      if (_currentRouteFilterPrefix != null) { // not ipv6
         RouteFilterLine line = _currentRouteFilterLine;
         _currentPsThens = line.getThens();
      }
   }

   @Override
   public void enterFwft_term(Fwft_termContext ctx) {
      String name = ctx.name.getText();
      Map<String, FwTerm> terms = _currentFilter.getTerms();
      _currentFwTerm = terms.get(name);
      if (_currentFwTerm == null) {
         _currentFwTerm = new FwTerm(name);
         terms.put(name, _currentFwTerm);
      }
   }

   @Override
   public void enterFwt_family(Fwt_familyContext ctx) {
      if (ctx.INET() != null) {
         _currentFirewallFamily = Family.INET;
      }
      else if (ctx.INET6() != null) {
         _currentFirewallFamily = Family.INET6;
      }
      else if (ctx.MPLS() != null) {
         _currentFirewallFamily = Family.MPLS;
      }
   }

   @Override
   public void enterFwt_filter(Fwt_filterContext ctx) {
      String name = ctx.name.getText();
      Map<String, FirewallFilter> filters = _configuration.getFirewallFilters();
      _currentFilter = filters.get(name);
      if (_currentFilter == null) {
         _currentFilter = new FirewallFilter(name, _currentFirewallFamily);
         filters.put(name, _currentFilter);
      }
   }

   @Override
   public void enterIfamt_address(Ifamt_addressContext ctx) {
      Set<Prefix> allPrefixes = _currentInterface.getAllPrefixes();
      Prefix prefix = new Prefix(ctx.IP_PREFIX().getText());
      _currentInterfacePrefix = prefix;
      if (_currentInterface.getPrimaryPrefix() == null) {
         _currentInterface.setPrimaryPrefix(prefix);
      }
      if (_currentInterface.getPreferredPrefix() == null) {
         _currentInterface.setPreferredPrefix(prefix);
      }
      allPrefixes.add(prefix);
      Ip ip = prefix.getAddress();
      _currentInterface.getAllPrefixIps().add(ip);
   }

   @Override
   public void enterIntt_named(Intt_namedContext ctx) {
      if (ctx.name == null) {
         _currentInterface = _currentRoutingInstance.getGlobalMasterInterface();
      }
      else {
         String ifaceName = ctx.name.getText();
         Map<String, Interface> interfaces = _currentRoutingInstance
               .getInterfaces();
         _currentInterface = interfaces.get(ifaceName);
         if (_currentInterface == null) {
            _currentInterface = new Interface(ifaceName);
            interfaces.put(ifaceName, _currentInterface);
         }
      }
      _currentMasterInterface = _currentInterface;
   }

   @Override
   public void enterIsisit_level(Isisit_levelContext ctx) {
      int level = toInt(ctx.DEC());
      switch (level) {
      case 1:
         _currentIsisInterfaceLevelSettings = _currentIsisInterface
               .getIsisSettings().getLevel1Settings();
         break;
      case 2:
         _currentIsisInterfaceLevelSettings = _currentIsisInterface
               .getIsisSettings().getLevel2Settings();
         break;
      default:
         throw new BatfishException("invalid IS-IS level: " + level);
      }
      _currentIsisInterfaceLevelSettings.setEnabled(true);
   }

   @Override
   public void enterIsist_interface(Isist_interfaceContext ctx) {
      Map<String, Interface> interfaces = _currentRoutingInstance
            .getInterfaces();
      String unitFullName = null;
      String name = ctx.id.name.getText();
      String unit = null;
      if (ctx.id.unit != null) {
         unit = ctx.id.unit.getText();
      }
      unitFullName = name + "." + unit;
      _currentIsisInterface = interfaces.get(name);
      if (_currentIsisInterface == null) {
         _currentIsisInterface = new Interface(name);
         interfaces.put(name, _currentIsisInterface);
      }
      if (unit != null) {
         Map<String, Interface> units = _currentIsisInterface.getUnits();
         _currentIsisInterface = units.get(unitFullName);
         if (_currentIsisInterface == null) {
            _currentIsisInterface = new Interface(unitFullName);
            units.put(unitFullName, _currentIsisInterface);
         }
      }
      _currentIsisInterface.getIsisSettings().setEnabled(true);
   }

   @Override
   public void enterIsist_level(Isist_levelContext ctx) {
      IsisSettings isisSettings = _currentRoutingInstance.getIsisSettings();
      int level = toInt(ctx.DEC());
      switch (level) {
      case 1:
         _currentIsisLevelSettings = isisSettings.getLevel1Settings();
         break;
      case 2:
         _currentIsisLevelSettings = isisSettings.getLevel2Settings();
         break;
      default:
         throw new BatfishException("invalid level: " + level);
      }
      _currentIsisLevelSettings.setEnabled(true);
   }

   @Override
   public void enterIt_unit(It_unitContext ctx) {
      String unit = ctx.num.getText();
      String unitFullName = _currentMasterInterface.getName() + "." + unit;
      Map<String, Interface> units = _currentMasterInterface.getUnits();
      _currentInterface = units.get(unitFullName);
      if (_currentInterface == null) {
         _currentInterface = new Interface(unitFullName);
         units.put(unitFullName, _currentInterface);
      }
   }

   @Override
   public void enterOt_area(Ot_areaContext ctx) {
      Ip areaIp = new Ip(ctx.area.getText());
      Map<Ip, OspfArea> areas = _currentRoutingInstance.getOspfAreas();
      _currentArea = areas.get(areaIp);
      if (_currentArea == null) {
         _currentArea = new OspfArea(areaIp);
         areas.put(areaIp, _currentArea);
      }
   }

   @Override
   public void enterPot_community(Pot_communityContext ctx) {
      String name = ctx.name.getText();
      Map<String, CommunityList> communityLists = _configuration
            .getCommunityLists();
      _currentCommunityList = communityLists.get(name);
      if (_currentCommunityList == null) {
         _currentCommunityList = new CommunityList(name);
         communityLists.put(name, _currentCommunityList);
      }
   }

   @Override
   public void enterPot_policy_statement(Pot_policy_statementContext ctx) {
      String name = ctx.name.getText();
      Map<String, PolicyStatement> policyStatements = _configuration
            .getPolicyStatements();
      _currentPolicyStatement = policyStatements.get(name);
      if (_currentPolicyStatement == null) {
         _currentPolicyStatement = new PolicyStatement(name);
         policyStatements.put(name, _currentPolicyStatement);
      }
      _currentPsTerm = _currentPolicyStatement.getSingletonTerm();
      _currentPsThens = _currentPsTerm.getThens();
   }

   @Override
   public void enterPot_prefix_list(Pot_prefix_listContext ctx) {
      String name = ctx.name.getText();
      Map<String, PrefixList> prefixLists = _configuration.getPrefixLists();
      _currentPrefixList = prefixLists.get(name);
      if (_currentPrefixList == null) {
         _currentPrefixList = new PrefixList(name);
         prefixLists.put(name, _currentPrefixList);
      }
   }

   @Override
   public void enterPst_term(Pst_termContext ctx) {
      String name = ctx.name.getText();
      Map<String, PsTerm> terms = _currentPolicyStatement.getTerms();
      _currentPsTerm = terms.get(name);
      if (_currentPsTerm == null) {
         _currentPsTerm = new PsTerm(name);
         terms.put(name, _currentPsTerm);
      }
      _currentPsThens = _currentPsTerm.getThens();
   }

   @Override
   public void enterRft_exact(Rft_exactContext ctx) {
      if (_currentRouteFilterPrefix != null) { // not ipv6
         RouteFilterLineExact fromRouteFilterExact = new RouteFilterLineExact(
               _currentRouteFilterPrefix);
         _currentRouteFilterLine = _currentRouteFilter
               .insertLine(fromRouteFilterExact);
      }
   }

   @Override
   public void enterRft_longer(Rft_longerContext ctx) {
      if (_currentRouteFilterPrefix != null) { // not ipv6
         RouteFilterLineLonger fromRouteFilterOrLonger = new RouteFilterLineLonger(
               _currentRouteFilterPrefix);
         _currentRouteFilterLine = _currentRouteFilter
               .insertLine(fromRouteFilterOrLonger);
      }
   }

   @Override
   public void enterRft_orlonger(Rft_orlongerContext ctx) {
      if (_currentRouteFilterPrefix != null) { // not ipv6
         RouteFilterLineOrLonger fromRouteFilterOrLonger = new RouteFilterLineOrLonger(
               _currentRouteFilterPrefix);
         _currentRouteFilterLine = _currentRouteFilter
               .insertLine(fromRouteFilterOrLonger);
      }
   }

   @Override
   public void enterRft_prefix_length_range(Rft_prefix_length_rangeContext ctx) {
      int minPrefixLength = toInt(ctx.low);
      int maxPrefixLength = toInt(ctx.high);
      if (_currentRouteFilterPrefix != null) { // not ipv6
         RouteFilterLineLengthRange fromRouteFilterLengthRange = new RouteFilterLineLengthRange(
               _currentRouteFilterPrefix, minPrefixLength, maxPrefixLength);
         _currentRouteFilterLine = _currentRouteFilter
               .insertLine(fromRouteFilterLengthRange);
      }
   }

   @Override
   public void enterRft_through(Rft_throughContext ctx) {
      if (_currentRouteFilterPrefix != null) { // not ipv6
         Prefix throughPrefix = new Prefix(ctx.IP_PREFIX().getText());
         RouteFilterLineThrough fromRouteFilterThrough = new RouteFilterLineThrough(
               _currentRouteFilterPrefix, throughPrefix);
         _currentRouteFilterLine = _currentRouteFilter
               .insertLine(fromRouteFilterThrough);
      }
   }

   @Override
   public void enterRft_upto(Rft_uptoContext ctx) {
      int maxPrefixLength = toInt(ctx.high);
      if (_currentRouteFilterPrefix != null) { // not ipv6
         RouteFilterLineUpTo fromRouteFilterUpTo = new RouteFilterLineUpTo(
               _currentRouteFilterPrefix, maxPrefixLength);
         _currentRouteFilterLine = _currentRouteFilter
               .insertLine(fromRouteFilterUpTo);
      }
   }

   @Override
   public void enterRibt_aggregate(Ribt_aggregateContext ctx) {
      if (ctx.IP_PREFIX() != null) {
         Prefix prefix = new Prefix(ctx.IP_PREFIX().getText());
         Map<Prefix, AggregateRoute> aggregateRoutes = _currentRib
               .getAggregateRoutes();
         _currentAggregateRoute = aggregateRoutes.get(prefix);
         if (_currentAggregateRoute == null) {
            _currentAggregateRoute = new AggregateRoute(prefix);
            aggregateRoutes.put(prefix, _currentAggregateRoute);
         }
      }
      else {
         _currentAggregateRoute = DUMMY_AGGREGATE_ROUTE;
      }
   }

   @Override
   public void enterRit_named_routing_instance(
         Rit_named_routing_instanceContext ctx) {
      String name;
      name = ctx.name.getText();
      _currentRoutingInstance = _configuration.getRoutingInstances().get(name);
      if (_currentRoutingInstance == null) {
         _currentRoutingInstance = new RoutingInstance(name);
         _configuration.getRoutingInstances()
               .put(name, _currentRoutingInstance);
      }
   }

   @Override
   public void enterRot_generate(Rot_generateContext ctx) {
      if (ctx.IP_PREFIX() != null) {
         Prefix prefix = new Prefix(ctx.IP_PREFIX().getText());
         Map<Prefix, GeneratedRoute> generatedRoutes = _currentRib
               .getGeneratedRoutes();
         _currentGeneratedRoute = generatedRoutes.get(prefix);
         if (_currentGeneratedRoute == null) {
            _currentGeneratedRoute = new GeneratedRoute(prefix);
            generatedRoutes.put(prefix, _currentGeneratedRoute);
         }
      }
      else if (ctx.IPV6_PREFIX() != null) {
         todo(ctx, F_IPV6);
      }
   }

   @Override
   public void enterRot_rib(Rot_ribContext ctx) {
      String name = ctx.name.getText();
      Map<String, RoutingInformationBase> ribs = _currentRoutingInstance
            .getRibs();
      _currentRib = ribs.get(name);
      if (_currentRib == null) {
         _currentRib = new RoutingInformationBase(name);
         ribs.put(name, _currentRib);
      }
   }

   @Override
   public void enterRst_route(Rst_routeContext ctx) {
      if (ctx.IP_PREFIX() != null) {
         Prefix prefix = new Prefix(ctx.IP_PREFIX().getText());
         Map<Prefix, StaticRoute> staticRoutes = _currentRib.getStaticRoutes();
         _currentStaticRoute = staticRoutes.get(prefix);
         if (_currentStaticRoute == null) {
            _currentStaticRoute = new StaticRoute(prefix);
            staticRoutes.put(prefix, _currentStaticRoute);
         }
      }
      else if (ctx.IPV6_PREFIX() != null) {
         _currentStaticRoute = DUMMY_STATIC_ROUTE;
      }
   }

   @Override
   public void enterS_firewall(S_firewallContext ctx) {
      _currentFirewallFamily = Family.INET;
   }

   @Override
   public void enterS_protocols_bgp(S_protocols_bgpContext ctx) {
      _currentBgpGroup = _currentRoutingInstance.getMasterBgpGroup();
   }

   @Override
   public void enterS_routing_options(S_routing_optionsContext ctx) {
      _currentRib = _currentRoutingInstance.getRibs().get(
            RoutingInformationBase.RIB_IPV4_UNICAST);
   }

   @Override
   public void exitAgt_as_path(Agt_as_pathContext ctx) {
      AsPath asPath = toAsPath(ctx.path);
      _currentAggregateRoute.setAsPath(asPath);
   }

   @Override
   public void exitAgt_preference(Agt_preferenceContext ctx) {
      int preference = toInt(ctx.preference);
      _currentAggregateRoute.setPreference(preference);
   }

   @Override
   public void exitAt_interface(At_interfaceContext ctx) {
      _currentOspfInterface = null;
   }

   @Override
   public void exitBpast_as(Bpast_asContext ctx) {
      int peerAs = toInt(ctx.as);
      _currentBgpGroup.setPeerAs(peerAs);
   }

   @Override
   public void exitBt_description(Bt_descriptionContext ctx) {
      String description = ctx.s_description().description.getText();
      _currentBgpGroup.setDescription(description);
   }

   @Override
   public void exitBt_export(Bt_exportContext ctx) {
      Policy_expressionContext expr = ctx.expr;
      if (expr.variable() != null) {
         String name = expr.variable().getText();
         _currentBgpGroup.getExportPolicies().add(name);
      }
      else {
         todo(ctx, F_COMPLEX_POLICY);
      }
   }

   @Override
   public void exitBt_import(Bt_importContext ctx) {
      Policy_expressionContext expr = ctx.expr;
      if (expr.variable() != null) {
         String name = expr.variable().getText();
         _currentBgpGroup.getImportPolicies().add(name);
      }
      else {
         todo(ctx, F_COMPLEX_POLICY);
      }
   }

   @Override
   public void exitBt_local_address(Bt_local_addressContext ctx) {
      if (ctx.IP_ADDRESS() != null) {
         Ip localAddress = new Ip(ctx.IP_ADDRESS().getText());
         _currentBgpGroup.setLocalAddress(localAddress);
      }
   }

   @Override
   public void exitBt_remove_private(Bt_remove_privateContext ctx) {
      _currentBgpGroup.setRemovePrivate(true);
   }

   @Override
   public void exitBt_type(Bt_typeContext ctx) {
      if (ctx.INTERNAL() != null) {
         _currentBgpGroup.setType(BgpGroupType.INTERNAL);
      }
      else if (ctx.EXTERNAL() != null) {
         _currentBgpGroup.setType(BgpGroupType.EXTERNAL);
      }
   }

   @Override
   public void exitCt_members(Ct_membersContext ctx) {
      if (ctx.community_regex() != null) {
         _currentCommunityList.getLines().add(
               new CommunityListLine(ctx.community_regex().getText()));
      }
      else if (ctx.standard_community() != null) {
         long communityVal = toCommunityLong(ctx.standard_community());
         String communityStr = org.batfish.util.Util
               .longToCommunity(communityVal);
         _currentCommunityList.getLines().add(
               new CommunityListLine(communityStr));
      }
      else if (ctx.extended_community() != null) {
         long communityVal = toCommunityLong(ctx.extended_community());
         String communityStr = org.batfish.util.Util
               .longToCommunity(communityVal);
         _currentCommunityList.getLines().add(
               new CommunityListLine(communityStr));
      }
   }

   @Override
   public void exitFromt_as_path(Fromt_as_pathContext ctx) {
      String name = ctx.name.getText();
      PsFromAsPath fromAsPath = new PsFromAsPath(name);
      _currentPsTerm.getFroms().add(fromAsPath);
   }

   @Override
   public void exitFromt_color(Fromt_colorContext ctx) {
      int color = toInt(ctx.color);
      PsFromColor fromColor = new PsFromColor(color);
      _currentPsTerm.getFroms().add(fromColor);
   }

   @Override
   public void exitFromt_community(Fromt_communityContext ctx) {
      String name = ctx.name.getText();
      PsFromCommunity fromCommunity = new PsFromCommunity(name);
      _currentPsTerm.getFroms().add(fromCommunity);
   }

   @Override
   public void exitFromt_interface(Fromt_interfaceContext ctx) {
      String name = ctx.id.name.getText();
      String unit = null;
      if (ctx.id.unit != null) {
         unit = ctx.id.unit.getText();
      }
      String unitFullName = name + "." + unit;
      Map<String, Interface> interfaces = _currentRoutingInstance
            .getInterfaces();
      Interface iface = interfaces.get(name);
      if (iface == null) {
         iface = new Interface(name);
         interfaces.put(name, iface);
      }
      PsFrom from;
      if (unit != null) {
         Map<String, Interface> units = iface.getUnits();
         iface = units.get(unitFullName);
         if (iface == null) {
            iface = new Interface(unitFullName);
            units.put(unitFullName, iface);
         }
         from = new PsFromInterface(unitFullName);
      }
      else {
         from = new PsFromInterface(name);
      }
      _currentPsTerm.getFroms().add(from);
   }

   @Override
   public void exitFromt_prefix_list(Fromt_prefix_listContext ctx) {
      String name = ctx.name.getText();
      PsFrom from = new PsFromPrefixList(name);
      _currentPsTerm.getFroms().add(from);
   }

   @Override
   public void exitFromt_protocol(Fromt_protocolContext ctx) {
      RoutingProtocol protocol = toRoutingProtocol(ctx.protocol);
      PsFromProtocol fromProtocol = new PsFromProtocol(protocol);
      _currentPsTerm.getFroms().add(fromProtocol);
   }

   @Override
   public void exitFromt_route_filter(Fromt_route_filterContext ctx) {
      _currentRouteFilterPrefix = null;
      _currentRouteFilter = null;
      _currentRouteFilterLine = null;
   }

   @Override
   public void exitFromt_route_filter_then(Fromt_route_filter_thenContext ctx) {
      _currentPsThens = _currentPsTerm.getThens();
   }

   @Override
   public void exitFwfromt_destination_address(
         Fwfromt_destination_addressContext ctx) {
      if (ctx.IP_PREFIX() != null) {
         Prefix prefix = new Prefix(ctx.IP_PREFIX().getText());
         FwFrom from = new FwFromDestinationAddress(prefix);
         _currentFwTerm.getFroms().add(from);
      }
   }

   @Override
   public void exitFwfromt_destination_port(Fwfromt_destination_portContext ctx) {
      if (ctx.port() != null) {
         int port = getPortNumber(ctx.port());
         SubRange subrange = new SubRange(port, port);
         FwFrom from = new FwFromDestinationPort(subrange);
         _currentFwTerm.getFroms().add(from);
      }
      else if (ctx.range() != null) {
         for (SubrangeContext subrangeContext : ctx.range().range_list) {
            int low = toInt(subrangeContext.low);
            int high = toInt(subrangeContext.high);
            SubRange subrange = new SubRange(low, high);
            FwFrom from = new FwFromDestinationPort(subrange);
            _currentFwTerm.getFroms().add(from);
         }
      }
   }

   @Override
   public void exitFwfromt_protocol(Fwfromt_protocolContext ctx) {
      IpProtocol protocol = toIpProtocol(ctx.ip_protocol());
      FwFrom from = new FwFromProtocol(protocol);
      _currentFwTerm.getFroms().add(from);
   }

   @Override
   public void exitFwfromt_source_address(Fwfromt_source_addressContext ctx) {
      if (ctx.IP_PREFIX() != null) {
         Prefix prefix = new Prefix(ctx.IP_PREFIX().getText());
         FwFrom from = new FwFromSourceAddress(prefix);
         _currentFwTerm.getFroms().add(from);
      }
   }

   @Override
   public void exitFwfromt_source_port(Fwfromt_source_portContext ctx) {
      if (ctx.port() != null) {
         int port = getPortNumber(ctx.port());
         SubRange subrange = new SubRange(port, port);
         FwFrom from = new FwFromSourcePort(subrange);
         _currentFwTerm.getFroms().add(from);
      }
      else if (ctx.range() != null) {
         for (SubrangeContext subrangeContext : ctx.range().range_list) {
            int low = toInt(subrangeContext.low);
            int high = toInt(subrangeContext.high);
            SubRange subrange = new SubRange(low, high);
            FwFrom from = new FwFromSourcePort(subrange);
            _currentFwTerm.getFroms().add(from);
         }
      }
   }

   @Override
   public void exitFwft_term(Fwft_termContext ctx) {
      _currentFwTerm = null;
   }

   @Override
   public void exitFwt_filter(Fwt_filterContext ctx) {
      _currentFilter = null;
   }

   @Override
   public void exitFwthent_accept(Fwthent_acceptContext ctx) {
      _currentFwTerm.getThens().add(FwThenAccept.INSTANCE);
   }

   @Override
   public void exitFwthent_discard(Fwthent_discardContext ctx) {
      _currentFwTerm.getThens().add(FwThenDiscard.INSTANCE);
   }

   @Override
   public void exitFwthent_next_term(Fwthent_next_termContext ctx) {
      _currentFwTerm.getThens().add(FwThenNextTerm.INSTANCE);
   }

   @Override
   public void exitFwthent_nop(Fwthent_nopContext ctx) {
      _currentFwTerm.getThens().add(FwThenNop.INSTANCE);
   }

   @Override
   public void exitFwthent_reject(Fwthent_rejectContext ctx) {
      _currentFwTerm.getThens().add(FwThenDiscard.INSTANCE);
   }

   @Override
   public void exitFwthent_routing_instance(Fwthent_routing_instanceContext ctx) {
      // TODO: implement
      _w.unimplemented(ConfigurationBuilder.F_FIREWALL_TERM_THEN_ROUTING_INSTANCE);
      _currentFwTerm.getThens().add(FwThenDiscard.INSTANCE);
   }

   @Override
   public void exitGt_metric(Gt_metricContext ctx) {
      int metric = toInt(ctx.metric);
      _currentGeneratedRoute.setMetric(metric);
   }

   @Override
   public void exitGt_policy(Gt_policyContext ctx) {
      if (_currentGeneratedRoute != null) { // not ipv6
         String policy = ctx.policy.getText();
         _currentGeneratedRoute.getPolicies().add(policy);
      }
   }

   @Override
   public void exitIfamat_preferred(Ifamat_preferredContext ctx) {
      _currentInterface.setPreferredPrefix(_currentInterfacePrefix);
   }

   @Override
   public void exitIfamat_primary(Ifamat_primaryContext ctx) {
      _currentInterface.setPrimaryPrefix(_currentInterfacePrefix);
   }

   @Override
   public void exitIfamt_address(Ifamt_addressContext ctx) {
      _currentInterfacePrefix = null;
   }

   @Override
   public void exitIfamt_filter(Ifamt_filterContext ctx) {
      FilterContext filter = ctx.filter();
      if (filter.filter_tail() != null) {
         if (filter.filter_tail().ft_direction() != null) {
            Ft_directionContext ftd = filter.filter_tail().ft_direction();
            String name = ftd.name.getText();
            DirectionContext direction = ftd.direction();
            if (direction.INPUT() != null) {
               _currentInterface.setIncomingFilter(name);
            }
            else if (direction.OUTPUT() != null) {
               _currentInterface.setOutgoingFilter(name);
            }
         }
      }
   }

   @Override
   public void exitIntt_named(Intt_namedContext ctx) {
      _currentInterface = null;
      _currentMasterInterface = null;
   }

   @Override
   public void exitIsisilt_enable(Isisilt_enableContext ctx) {
      _currentIsisInterfaceLevelSettings.setEnabled(true);
   }

   @Override
   public void exitIsisilt_metric(Isisilt_metricContext ctx) {
      int metric = toInt(ctx.DEC());
      _currentIsisInterfaceLevelSettings.setMetric(metric);
   }

   @Override
   public void exitIsisilt_te_metric(Isisilt_te_metricContext ctx) {
      int teMetric = toInt(ctx.DEC());
      _currentIsisInterfaceLevelSettings.setTeMetric(teMetric);
   }

   @Override
   public void exitIsisit_level(Isisit_levelContext ctx) {
      _currentIsisInterfaceLevelSettings = null;
   }

   @Override
   public void exitIsisit_passive(Isisit_passiveContext ctx) {
      _currentIsisInterface.getIsisSettings().setPassive(true);
   }

   @Override
   public void exitIsisit_point_to_point(Isisit_point_to_pointContext ctx) {
      _currentIsisInterface.getIsisSettings().setPointToPoint(true);
   }

   @Override
   public void exitIsislt_disable(Isislt_disableContext ctx) {
      _currentIsisLevelSettings.setEnabled(false);
   }

   @Override
   public void exitIsislt_wide_metrics_only(Isislt_wide_metrics_onlyContext ctx) {
      _currentIsisLevelSettings.setWideMetricsOnly(true);
   }

   @Override
   public void exitIsist_export(Isist_exportContext ctx) {
      Set<String> policies = new LinkedHashSet<String>();
      for (VariableContext policy : ctx.policies) {
         policies.add(policy.getText());
      }
      _currentRoutingInstance.getIsisSettings().getExportPolicies()
            .addAll(policies);
   }

   @Override
   public void exitIsist_interface(Isist_interfaceContext ctx) {
      _currentIsisInterface = null;
   }

   @Override
   public void exitIsist_level(Isist_levelContext ctx) {
      _currentIsisLevelSettings = null;
   }

   @Override
   public void exitIsist_no_ipv4_routing(Isist_no_ipv4_routingContext ctx) {
      _currentRoutingInstance.getIsisSettings().setNoIpv4Routing(true);
   }

   @Override
   public void exitIsistet_credibility_protocol_preference(
         Isistet_credibility_protocol_preferenceContext ctx) {
      _currentRoutingInstance.getIsisSettings()
            .setTrafficEngineeringCredibilityProtocolPreference(true);
   }

   @Override
   public void exitIsistet_family_shortcuts(Isistet_family_shortcutsContext ctx) {
      if (ctx.INET6() != null) {
         todo(ctx, F_IPV6);
      }
      else { // ipv4
         _currentRoutingInstance.getIsisSettings()
               .setTrafficEngineeringShortcuts(true);
      }
   }

   @Override
   public void exitIsofamt_address(Isofamt_addressContext ctx) {
      IsoAddress address = new IsoAddress(ctx.ISO_ADDRESS().getText());
      _currentInterface.setIsoAddress(address);
   }

   @Override
   public void exitIt_disable(It_disableContext ctx) {
      _currentInterface.setActive(false);
   }

   @Override
   public void exitIt_enable(It_enableContext ctx) {
      _currentInterface.setActive(true);
   }

   @Override
   public void exitIt_unit(It_unitContext ctx) {
      _currentInterface = _currentMasterInterface;
   }

   @Override
   public void exitLast_loops(Last_loopsContext ctx) {
      todo(ctx, F_BGP_LOCAL_AS_LOOPS);
   }

   @Override
   public void exitLast_number(Last_numberContext ctx) {
      int localAs = toInt(ctx.as);
      _currentBgpGroup.setLocalAs(localAs);
   }

   @Override
   public void exitLast_private(Last_privateContext ctx) {
      todo(ctx, F_BGP_LOCAL_AS_PRIVATE);
   }

   @Override
   public void exitOt_area(Ot_areaContext ctx) {
      _currentArea = null;
   }

   @Override
   public void exitOt_export(Ot_exportContext ctx) {
      String name = ctx.name.getText();
      _currentRoutingInstance.getOspfExportPolicies().add(name);
   }

   @Override
   public void exitPlt_network(Plt_networkContext ctx) {
      Prefix prefix = new Prefix(ctx.network.getText());
      _currentPrefixList.getPrefixes().add(prefix);
   }

   @Override
   public void exitPot_community(Pot_communityContext ctx) {
      _currentCommunityList = null;
   }

   @Override
   public void exitPot_policy_statement(Pot_policy_statementContext ctx) {
      _currentPolicyStatement = null;
   }

   public void exitPot_prefix_list(Pot_prefix_listContext ctx) {
      _currentPrefixList = null;
   }

   @Override
   public void exitPst_term_tail(Pst_term_tailContext ctx) {
      _currentPsTerm = null;
      _currentPsThens = null;
   }

   @Override
   public void exitRibt_aggregate(Ribt_aggregateContext ctx) {
      _currentAggregateRoute = null;
   }

   @Override
   public void exitRot_autonomous_system(Rot_autonomous_systemContext ctx) {
      int as = toInt(ctx.as);
      _currentRoutingInstance.setAs(as);
   }

   @Override
   public void exitRot_generate(Rot_generateContext ctx) {
      _currentGeneratedRoute = null;
   }

   @Override
   public void exitRot_router_id(Rot_router_idContext ctx) {
      Ip id = new Ip(ctx.id.getText());
      _currentRoutingInstance.setRouterId(id);
   }

   @Override
   public void exitRot_static(Rot_staticContext ctx) {
      _currentStaticRoute = null;
   }

   @Override
   public void exitS_firewall(S_firewallContext ctx) {
      _currentFirewallFamily = null;
   }

   @Override
   public void exitS_protocols_bgp(S_protocols_bgpContext ctx) {
      _currentBgpGroup = null;
   }

   @Override
   public void exitS_routing_options(S_routing_optionsContext ctx) {
      _currentRib = null;
   }

   @Override
   public void exitSrt_discard(Srt_discardContext ctx) {
      _currentStaticRoute.setDrop(true);
   }

   @Override
   public void exitSrt_reject(Srt_rejectContext ctx) {
      _currentStaticRoute.setDrop(true);
   }

   @Override
   public void exitSrt_tag(Srt_tagContext ctx) {
      int tag = toInt(ctx.tag);
      _currentStaticRoute.setTag(tag);
   }

   @Override
   public void exitSt_default_address_selection(
         St_default_address_selectionContext ctx) {
      _configuration.setDefaultAddressSelection(true);
   }

   @Override
   public void exitSt_host_name(St_host_nameContext ctx) {
      String hostname = ctx.variable().getText();
      _currentRoutingInstance.setHostname(hostname);
   }

   @Override
   public void exitTht_accept(Tht_acceptContext ctx) {
      _currentPsThens.add(PsThenAccept.INSTANCE);
   }

   @Override
   public void exitTht_community_add(Tht_community_addContext ctx) {
      String name = ctx.name.getText();
      PsThenCommunityAdd then = new PsThenCommunityAdd(name, _configuration);
      _currentPsThens.add(then);
   }

   @Override
   public void exitTht_community_delete(Tht_community_deleteContext ctx) {
      String name = ctx.name.getText();
      PsThenCommunityDelete then = new PsThenCommunityDelete(name);
      _currentPsThens.add(then);
   }

   @Override
   public void exitTht_community_set(Tht_community_setContext ctx) {
      String name = ctx.name.getText();
      PsThenCommunitySet then = new PsThenCommunitySet(name);
      _currentPsThens.add(then);
   }

   @Override
   public void exitTht_local_preference(Tht_local_preferenceContext ctx) {
      int localPreference = toInt(ctx.localpref);
      PsThenLocalPreference then = new PsThenLocalPreference(localPreference);
      _currentPsThens.add(then);
   }

   @Override
   public void exitTht_metric(Tht_metricContext ctx) {
      int metric = toInt(ctx.metric);
      PsThenMetric then = new PsThenMetric(metric);
      _currentPsThens.add(then);
   }

   @Override
   public void exitTht_next_hop(Tht_next_hopContext ctx) {
      PsThen then;
      if (ctx.IP_ADDRESS() != null) {
         Ip nextHopIp = new Ip(ctx.IP_ADDRESS().getText());
         then = new PsThenNextHopIp(nextHopIp);
      }
      else {
         todo(ctx, F_POLICY_TERM_THEN_NEXT_HOP);
         return;
      }
      _currentPsThens.add(then);
   }

   @Override
   public void exitTht_reject(Tht_rejectContext ctx) {
      _currentPsThens.add(PsThenReject.INSTANCE);
   }

   public JuniperVendorConfiguration getConfiguration() {
      return _configuration;
   }

   private AsPath toAsPath(As_path_exprContext path) {
      AsPath asPath = new AsPath();
      for (As_unitContext ctx : path.items) {
         AsSet asSet = new AsSet();
         if (ctx.DEC() != null) {
            asSet.add(toInt(ctx.DEC()));
         }
         else {
            for (Token token : ctx.as_set().items) {
               asSet.add(toInt(token));
            }
         }
         asPath.add(asSet);
      }
      return asPath;
   }

   private long toCommunityLong(Ec_namedContext ctx) {
      ExtendedCommunity ec = new ExtendedCommunity(ctx.getText());
      return ec.asLong();
   }

   private long toCommunityLong(Extended_communityContext ctx) {
      if (ctx.ec_literal() != null) {
         throw new BatfishException(
               "literal extended communities not supported");
      }
      else if (ctx.ec_named() != null) {
         return toCommunityLong(ctx.ec_named());
      }
      else {
         throw new BatfishException("invalid extended community");
      }
   }

   private void todo(ParserRuleContext ctx, String feature) {
      _w.todo(ctx, feature, _parser, _text);
      _unimplementedFeatures.add("Juniper: " + feature);
   }

}
