package org._127001.frymaster.permissionstest;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class PermissionsTest extends JavaPlugin implements Listener {
    public void onDisable() {
        // TODO: Place any custom disable code here.
    }

    public void onEnable() {
        PluginManager pm = this.getServer().getPluginManager();
        pm.addPermission(new Permission("test1", PermissionDefault.FALSE));
        pm.addPermission(new Permission("test2", PermissionDefault.FALSE));
        pm.addPermission(new Permission("test3", PermissionDefault.FALSE));
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().sendMessage("Welcome, " + event.getPlayer().getDisplayName() + "!");
        Player p = event.getPlayer();
        PermissionAttachment pa = p.addAttachment(this);       
        
        this.getLogger().info("Before any permission commands:");
        showPerms(p); // All should be false and unset
        
        Map<String,Boolean> m1 = new HashMap<String,Boolean>();
        m1.put("test1", Boolean.TRUE);
        m1.put("test2", Boolean.TRUE);
        pa.setPermissions(m1);
        this.getLogger().info("After setPermissions:");
        showPerms(p); // test1 and 2 should be true and set
        
        List<String> l1 = new LinkedList<String>();
        l1.add("test1");
        pa.unsetPermissions(l1);
        this.getLogger().info("After unSetPermissions:");
        showPerms(p);  // test 2 only should still be true and set
        
        Map<String,Boolean> m2 = new HashMap<String,Boolean>();
        m2.put("test3", Boolean.FALSE);
        pa.setPermissions(m2);
        this.getLogger().info("After second setPermissions:");
        showPerms(p);  // test 2 should still be true and set, test 3 should be false and set
        
        Map<String,Boolean> m3 = new HashMap<String,Boolean>();
        m3.put("test1", Boolean.TRUE);
        m3.put("test2", Boolean.TRUE);
        pa.resetPermissions(m3);
        this.getLogger().info("After resetPermissions:");
        showPerms(p); // test 1 and 2 should be true and set
                
    }
    
    public void showPerms(Permissible p) {
        this.getLogger().info("test1 permission value: " + Boolean.toString(p.hasPermission("test1")));
        this.getLogger().info("test2 permission value: " + Boolean.toString(p.hasPermission("test2")));
        this.getLogger().info("test3 permission value: " + Boolean.toString(p.hasPermission("test3")));
        this.getLogger().info("test1 permission set  : " + Boolean.toString(p.isPermissionSet("test1")));
        this.getLogger().info("test2 permission set  : " + Boolean.toString(p.isPermissionSet("test2")));
        this.getLogger().info("test3 permission set  : " + Boolean.toString(p.isPermissionSet("test3")));
        this.getLogger().info(""); //This line intentionally left blank ;)
    }
}

