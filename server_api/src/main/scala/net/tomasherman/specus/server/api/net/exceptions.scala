package net.tomasherman.specus.server.api.net

import net.tomasherman.specus.common.api.net.Packet

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


class PacketEncoderNotFoundException(val packet: Packet) extends Exception

class BufferDecoderNotFoundException(val packetId: Byte) extends Exception

class DecodingErrorException(val expected: String, val value: Any) extends Exception