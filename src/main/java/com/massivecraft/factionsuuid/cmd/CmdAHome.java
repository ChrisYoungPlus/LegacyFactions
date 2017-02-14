package com.massivecraft.factionsuuid.cmd;

import org.bukkit.event.player.PlayerTeleportEvent;

import com.massivecraft.factionsuuid.Permission;
import com.massivecraft.factionsuuid.TL;
import com.massivecraft.factionsuuid.entity.FPlayer;
import com.massivecraft.factionsuuid.entity.Faction;

public class CmdAHome extends FCommand {

    public CmdAHome() {
        super();
        this.aliases.add("ahome");

        this.requiredArgs.add("player name");

        this.permission = Permission.AHOME.node;
        this.disableOnLock = false;

        senderMustBePlayer = false;
        senderMustBeMember = false;
        senderMustBeModerator = false;
        senderMustBeAdmin = false;
    }

    @Override
    public void perform() {
        FPlayer target = argAsBestFPlayerMatch(0);
        if (target == null) {
            msg(TL.GENERIC_NOPLAYERMATCH, argAsString(0));
            return;
        }

        if (target.isOnline()) {
            Faction faction = target.getFaction();
            if (faction.hasHome()) {
                target.getPlayer().teleport(faction.getHome(), PlayerTeleportEvent.TeleportCause.PLUGIN);
                msg(TL.COMMAND_AHOME_SUCCESS, target.getName());
                target.msg(TL.COMMAND_AHOME_TARGET);
            } else {
                msg(TL.COMMAND_AHOME_NOHOME, target.getName());
            }
        } else {
            msg(TL.COMMAND_AHOME_OFFLINE, target.getName());
        }
    }

    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_AHOME_DESCRIPTION;
    }
}
