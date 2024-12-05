import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SeatIdentifier {
    public static void main(String[] args) throws IOException {
        try (Stream<String> lines = Files.lines(Paths.get("data/testData.bat"))) {
            System.out.print(String.format("%s\n",
                "------------------------------------------------------------------------------------------------------------------------------------") +
                Stream.concat(
                    lines.map(line -> Integer.parseInt(line.chars()
                        .mapToObj(c -> (char) c == 'L' || (char) c == 'D' || (char) c == 'F' ? "0" : "1")
                        .collect(Collectors.joining()), 2)),
                    Stream.iterate(0, i -> i < 8, i -> i + 1)
                        .flatMap(coach -> Stream.iterate(0, j -> j < 2, j -> j + 1)
                            .flatMap(deck -> Stream.iterate(0, k -> k < 16, k -> k + 1)
                                .flatMap(row -> Stream.iterate(0, l -> l < 4, l -> l + 1)
                                    .map(col -> coach * 128 + deck * 64 + row * 4 + col))))
                )
                .filter(seatID -> {
                    
                    return Stream.of(((seatID >> 6) & 0x1) == 0 ? Stream.of(
                        ((seatID >> 2) & 0xF) == 0 && (seatID & 0x3) <= 1,
                        ((seatID >> 2) & 0xF) == 15 && ((seatID & 0x3) >= 2 || (seatID & 0x3) == 1),
                        ((seatID >> 2) & 0xF) == 14 && (seatID & 0x3) >= 2,
                        ((seatID >> 2) & 0xF) == 13 && (seatID & 0x3) >= 2
                        ).noneMatch(b -> b) :
                        Stream.of(
                            ((seatID >> 2) & 0xF) == 15 && (seatID & 0x3) >= 2,
                            ((seatID >> 2) & 0xF) == 14 && (seatID & 0x3) >= 2,
                            ((seatID >> 2) & 0xF) == 13 && (seatID & 0x3) == 2
                        ).noneMatch(b -> b))
                        .findFirst()
                        .orElse(false);
                })
                .collect(
                    Collectors.teeing(
                        Collectors.reducing((a, b) -> a ^ b),
                        Collectors.maxBy(Integer::compareTo),
                        (missingSeatID, highestSeatID) -> String.format(
                            "%s: coach %d, deck %d, row %d, column %d, seat ID %d.\n====================================================================================================================================\nHighest Seat ID: %d",
                            Stream.of(
                                String.format("%10s", Integer.toBinaryString(missingSeatID.orElse(0)))
                                    .replace(' ', '0')
                            )
                            .map(binary -> Stream.of(
                                binary.substring(0, 3).chars()
                                    .mapToObj(c -> String.valueOf((char)(c == '0' ? 'L' : 'R')))
                                    .collect(Collectors.joining()),
                                String.valueOf(binary.charAt(3) == '0' ? 'D' : 'U'),
                                binary.substring(4, 8).chars()
                                    .mapToObj(c -> String.valueOf((char)(c == '0' ? 'F' : 'B')))
                                    .collect(Collectors.joining()),
                                binary.substring(8).chars()
                                    .mapToObj(c -> String.valueOf((char)(c == '0' ? 'L' : 'R')))
                                    .collect(Collectors.joining())
                            )
                            .collect(Collectors.joining()))
                            .findFirst()
                            .orElse(""),
                            (missingSeatID.orElse(0) >> 7) & 0x7,
                            (missingSeatID.orElse(0) >> 6) & 0x1,
                            (missingSeatID.orElse(0) >> 2) & 0xF,
                            missingSeatID.orElse(0) & 0x3,
                            missingSeatID.orElse(0),
                            highestSeatID.orElse(0)
                        )
                    )
                ));
        }
    }
}