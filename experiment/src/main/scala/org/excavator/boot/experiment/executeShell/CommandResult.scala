package org.excavator.boot.experiment.executeShell

final case class CommandResult (exitCode: Int,
                                stdout:String,
                                stderr:String
                               )
