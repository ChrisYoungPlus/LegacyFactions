package com.massivecraft.factionsuuid.cmd;

import com.massivecraft.factionsuuid.Permission;
import com.massivecraft.factionsuuid.TL;
import com.massivecraft.factionsuuid.entity.FPlayer;
import com.massivecraft.factionsuuid.entity.FPlayerColl;
import com.massivecraft.factionsuuid.entity.Faction;

public class CmdPeaceful extends FCommand {

    public CmdPeaceful() {
        super();
        this.aliases.add("peaceful");

        this.requiredArgs.add("faction tag");
        //this.optionalArgs.put("", "");

        this.permission = Permission.SET_PEACEFUL.node;
        this.disableOnLock = true;

        senderMustBePlayer = false;
        senderMustBeMember = false;
        senderMustBeModerator = false;
        senderMustBeAdmin = false;
    }

    @Override
    public void perform() {
        Faction faction = this.argAsFaction(0);
        if (faction == null) {
            return;
        }

        String change;
        if (faction.isPeaceful()) {
            change = TL.COMMAND_PEACEFUL_REVOKE.toString();
            faction.setPeaceful(false);
        } else {
            change = TL.COMMAND_PEACEFUL_GRANT.toString();
            faction.setPeaceful(true);
        }

        // Inform all players
        for (FPlayer fplayer : FPlayerColl.getInstance().getOnlinePlayers()) {
            String blame = (fme == null ? TL.GENERIC_SERVERADMIN.toString() : fme.describeTo(fplayer, true));
            if (fplayer.getFaction() == faction) {
                fplayer.msg(TL.COMMAND_PEACEFUL_YOURS, blame, change);
            } else {
                fplayer.msg(TL.COMMAND_PEACEFUL_OTHER, blame, change, faction.getTag(fplayer));
            }
        }

    }

    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_PEACEFUL_DESCRIPTION;
    }

}
