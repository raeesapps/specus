package net.tomasherman.specus.server.api.plugin.definitions

/**
 * This file is part of Specus.
 *
 * Specus is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Specus is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 *
 * You should have received a copy of the GNU General Public License
 * along with Specus.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

case class PluginDependencies(dep:List[PluginDependency])
case class PluginDependency(identifier:PluginIdentifier,version:PluginVersionConstraint)

trait PluginVersionConstraint {
  def matches(other:PluginVersion):Option[Boolean]
}

case class Interval(bounds:(PluginVersion,PluginVersion)) extends PluginVersionConstraint{
 def matches(other: PluginVersion) = ((bounds._1 <= other),(bounds._2 > other)) match {
   case (Some(x),Some(y)) => Some(x && y)
   case _ => None
 }
}
case class EqGt(version:PluginVersion) extends PluginVersionConstraint{
  def matches(other: PluginVersion) = version <= other
}

