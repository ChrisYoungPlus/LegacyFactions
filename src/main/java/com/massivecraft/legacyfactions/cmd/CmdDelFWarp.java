package com.massivecraft.legacyfactions.cmd;

import com.massivecraft.legacyfactions.Factions;
import com.massivecraft.legacyfactions.Permission;
import com.massivecraft.legacyfactions.TL;
import com.massivecraft.legacyfactions.entity.FPlayer;

public class CmdDelFWarp extends FCommand {

    public CmdDelFWarp() {
        super();
        this.aliases.add("delwarp");
        this.aliases.add("dw");
        this.aliases.add("deletewarp");
        this.requiredArgs.add("warp name");
        this.senderMustBeMember = true;
        this.senderMustBeModerator = true;
        this.senderMustBePlayer = true;
        this.permission = Permission.SETWARP.node;
    }

    @Override
    public void perform() {
        String warp = argAsString(0);
        if (myFaction.isWarp(warp)) {
            if (!transact(fme)) {
                return;
            }
            myFaction.removeWarp(warp);
            fme.msg(TL.COMMAND_DELFWARP_DELETED, warp);
        } else {
            fme.msg(TL.COMMAND_DELFWARP_INVALID, warp);
        }
    }

    private boolean transact(FPlayer player) {
        return !Factions.get().getConfig().getBoolean("warp-cost.enabled", false) || player.isAdminBypassing() || payForCommand(Factions.get().getConfig().getDouble("warp-cost.delwarp", 5), TL.COMMAND_DELFWARP_TODELETE.toString(), TL.COMMAND_DELFWARP_FORDELETE.toString());
    }

    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_DELFWARP_DESCRIPTION;
    }
}
