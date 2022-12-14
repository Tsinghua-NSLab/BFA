package org.batfish.representation.cisco;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.batfish.representation.IsisInterfaceMode;
import org.batfish.representation.Prefix;
import org.batfish.representation.SwitchportEncapsulationType;
import org.batfish.representation.SwitchportMode;
import org.batfish.util.SubRange;

public class Interface implements Serializable {

   private static final double DEFAULT_INTERFACE_BANDWIDTH = 1E12;

   private static final double FAST_ETHERNET_BANDWIDTH = 100E6;

   private static final double GIGABIT_ETHERNET_BANDWIDTH = 1E9;

   /**
    * dirty hack: just chose a very large number
    */
   private static final double LOOPBACK_BANDWIDTH = 1E12;

   private static final long serialVersionUID = 1L;

   private static final double TEN_GIGABIT_ETHERNET_BANDWIDTH = 10E9;

   public static double getDefaultBandwidth(String name) {
      Double bandwidth = null;
      if (name.startsWith("FastEthernet")) {
         bandwidth = FAST_ETHERNET_BANDWIDTH;
      }
      else if (name.startsWith("GigabitEthernet")) {
         bandwidth = GIGABIT_ETHERNET_BANDWIDTH;
      }
      else if (name.startsWith("TenGigabitEthernet")) {
         bandwidth = TEN_GIGABIT_ETHERNET_BANDWIDTH;
      }
      else if (name.startsWith("Vlan")) {
         bandwidth = null;
      }
      else if (name.startsWith("Loopback")) {
         bandwidth = LOOPBACK_BANDWIDTH;
      }
      if (bandwidth == null) {
         bandwidth = DEFAULT_INTERFACE_BANDWIDTH;
      }
      return bandwidth;
   }

   private int _accessVlan;

   private boolean _active;

   private ArrayList<SubRange> _allowedVlans;

   private Double _bandwidth;
   
   private Integer _channelGroup;

   private String _description;
   
   private boolean _explicitActive;

   private String _incomingFilter;

   private Integer _isisCost;

   private IsisInterfaceMode _isisInterfaceMode;

   private String _name;

   private int _nativeVlan;

   private Integer _ospfCost;

   private int _ospfDeadInterval;

   private int _ospfHelloMultiplier;

   private String _outgoingFilter;

   private Prefix _prefix;

   private String _routingPolicy;

   private Set<Prefix> _secondaryPrefixes;

   private SwitchportMode _switchportMode;

   private SwitchportEncapsulationType _switchportTrunkEncapsulation;

   private String _vrf;

   public Interface(String name) {
      _name = name;
      _active = true;
      _channelGroup = null;
      _explicitActive = false;
      _nativeVlan = 1;
      _switchportMode = SwitchportMode.NONE;
      _allowedVlans = new ArrayList<SubRange>();
      _secondaryPrefixes = new LinkedHashSet<Prefix>();
      _isisInterfaceMode = IsisInterfaceMode.UNSET;
   }

   public void addAllowedRanges(List<SubRange> ranges) {
      _allowedVlans.addAll(ranges);
   }

   public int getAccessVlan() {
      return _accessVlan;
   }

   public boolean getActive() {
      return _active;
   }

   public List<SubRange> getAllowedVlans() {
      return _allowedVlans;
   }

   public Double getBandwidth() {
      return _bandwidth;
   }
   
   public Integer getChannelGroup() {
      return _channelGroup;
   }

   public String getDescription() {
      return _description;
   }
   
   public boolean getExplicitActive() {
      return _explicitActive;
   }

   public String getIncomingFilter() {
      return _incomingFilter;
   }

   public Integer getIsisCost() {
      return _isisCost;
   }

   public IsisInterfaceMode getIsisInterfaceMode() {
      return _isisInterfaceMode;
   }

   public String getName() {
      return _name;
   }

   public int getNativeVlan() {
      return _nativeVlan;
   }

   public Integer getOspfCost() {
      return _ospfCost;
   }

   public int getOspfDeadInterval() {
      return _ospfDeadInterval;
   }

   public int getOspfHelloMultiplier() {
      return _ospfHelloMultiplier;
   }

   public String getOutgoingFilter() {
      return _outgoingFilter;
   }

   public Prefix getPrefix() {
      return _prefix;
   }

   public String getRoutingPolicy() {
      return _routingPolicy;
   }

   public Set<Prefix> getSecondaryPrefixes() {
      return _secondaryPrefixes;
   }

   public SwitchportMode getSwitchportMode() {
      return _switchportMode;
   }

   public SwitchportEncapsulationType getSwitchportTrunkEncapsulation() {
      return _switchportTrunkEncapsulation;
   }

   public String getVrf() {
      return _vrf;
   }

   public void setAccessVlan(int vlan) {
      _accessVlan = vlan;
   }

   public void setActive(boolean active) {
      _active = active;
   }

   public void setBandwidth(Double bandwidth) {
      _bandwidth = bandwidth;
   }
   
   public void setChannelGroup(Integer channelGroup) {
      _channelGroup = channelGroup;
   }

   public void setDescription(String description) {
      _description = description;
   }
   
   public void setExplicitActive(boolean explicitActive) {
      _explicitActive = explicitActive;
   }

   public void setIncomingFilter(String accessListName) {
      _incomingFilter = accessListName;
   }

   public void setIsisCost(Integer isisCost) {
      _isisCost = isisCost;
   }

   public void setIsisInterfaceMode(IsisInterfaceMode mode) {
      _isisInterfaceMode = mode;
   }

   public void setNativeVlan(int vlan) {
      _nativeVlan = vlan;
   }

   public void setOspfCost(int ospfCost) {
      _ospfCost = ospfCost;
   }

   public void setOspfDeadInterval(int seconds) {
      _ospfDeadInterval = seconds;
   }

   public void setOspfHelloMultiplier(int multiplier) {
      _ospfHelloMultiplier = multiplier;
   }

   public void setOutgoingFilter(String accessListName) {
      _outgoingFilter = accessListName;
   }

   public void setPrefix(Prefix prefix) {
      _prefix = prefix;
   }

   public void setRoutingPolicy(String routingPolicy) {
      _routingPolicy = routingPolicy;
   }

   public void setSwitchportMode(SwitchportMode switchportMode) {
      _switchportMode = switchportMode;
   }

   public void setSwitchportTrunkEncapsulation(
         SwitchportEncapsulationType encapsulation) {
      _switchportTrunkEncapsulation = encapsulation;
   }

   public void setVrf(String vrf) {
      _vrf = vrf;
   }

}
